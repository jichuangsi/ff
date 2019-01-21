package cn.com.fintheircing.admin.proxy.service;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.constant.RoleCodes;
import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.common.utils.MatrixToImageWriter;
import cn.com.fintheircing.admin.login.entity.AdminClientLoginInfo;
import cn.com.fintheircing.admin.login.service.AdminLoginService;
import cn.com.fintheircing.admin.proxy.dao.mapper.IAdminClientInfoMapper;
import cn.com.fintheircing.admin.proxy.dao.mapper.ICommissionMapper;
import cn.com.fintheircing.admin.proxy.dao.mapper.ISpreadMapper;
import cn.com.fintheircing.admin.proxy.dao.repository.IAdminClientInfoRepository;
import cn.com.fintheircing.admin.proxy.dao.repository.IAdminClientLoginInfoRepository;
import cn.com.fintheircing.admin.proxy.dao.repository.ICommissionRepository;
import cn.com.fintheircing.admin.proxy.dao.repository.ISpreadRepository;
import cn.com.fintheircing.admin.proxy.entity.ProxyCommission;
import cn.com.fintheircing.admin.proxy.entity.ProxySpread;
import cn.com.fintheircing.admin.proxy.exception.ProxyException;
import cn.com.fintheircing.admin.proxy.model.EmployeeModel;
import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import cn.com.fintheircing.admin.proxy.model.SpreadModel;
import cn.com.fintheircing.admin.proxy.utils.MappingModel2EntityConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@Service
public class ProxyService {

    @Value("${custom.admin.pwd}")
    private String pwd;
    @Value("${custom.admin.inviteLink}")
    private String inviteLink;
    @Value("${custom.admin.codeWidth}")
    private int codeWidth;
    @Value("${custom.admin.codeHeight}")
    private int codeHeight;
    @Value("${custom.admin.picFormat}")
    private String picFormat;
    @Value("${custom.admin.picSavePath}")
    private String picSavePath;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Resource
    private IAdminClientInfoRepository adminClientInfoRepository;
    @Resource
    private IAdminClientLoginInfoRepository adminClientLoginInfoRepository;
    @Resource
    private ICommissionRepository commissionRepository;
    @Resource
    private ICommissionMapper commissionMapper;
    @Resource
    private IAdminClientInfoMapper adminClientInfoMapper;
    @Resource
    private ISpreadMapper spreadMapper;
    @Resource
    private ISpreadRepository spreadRepository;
    @Resource
    private AdminLoginService adminLoginService;

    //获取代理信息列表
    public PageInfo<ProxyModel> getProxyList(UserTokenInfo userInfo , ProxyModel proxyModel) throws ProxyException{
        proxyModel.setProxyId(userInfo.getUuid());
        PageInfo<ProxyModel> pageInfo = new PageInfo<ProxyModel>();
        if(RoleCodes.ROLE_KEY_STRING.get("M").equals(userInfo.getRoleGrade())){
            pageInfo = manageProxy(userInfo,proxyModel);
        }else {
            proxyModel.setRoleGrade(userInfo.getRoleGrade());
            proxyModel.setProxyId(userInfo.getUuid());
            pageInfo = commonProxy(proxyModel);
        }
        return pageInfo;
    }

    //保存代理或员工
    @Transactional(rollbackFor=Exception.class)
    public String saveProxy(UserTokenInfo userInfo , ProxyModel proxyModel) throws ProxyException{
        UserTokenInfo userTokenInfo = new UserTokenInfo();
        userTokenInfo.setPwd(changePwd(pwd));
        userTokenInfo.setLoginName(proxyModel.getProxyName());
        if (adminLoginService.countAdmin(userTokenInfo)>0){
            throw new ProxyException(ResultCode.PROXY_ISEXIST_ERR);
        }
        AdminClientInfo info = MappingModel2EntityConverter.CONVERTERFORPROXYMODEL(proxyModel);
        info.setBossId(userInfo.getUuid());
        /*userInfo.setRoleGrade(1);*/
        if(RoleCodes.ROLE_KEY_STRING.get("E")==proxyModel.getRoleGrade()) {//添加员工
           info.setRoleGrade(proxyModel.getRoleGrade());
        } else if(RoleCodes.ROLE_KEY_STRING.get("A")==proxyModel.getRoleGrade()
                &&userInfo.getRoleGrade()<RoleCodes.ROLE_KEY_STRING.get("S")){//非添加员工，只能创建低于自己一级
            info.setRoleGrade(userInfo.getRoleGrade() + 1);
        }/*else if (proxyModel.getRoleGrade()==100){   //后要注释，模拟注册系统管理员
            info.setRoleGrade(RoleCodes.ROLE_KEY_STRING.get("M"));
        }*/ else {
            throw new ProxyException(ResultCode.POWER_VISIT_ERR);
        }
        info.setStatus(AdminClientInfo.STATUS_EXIST);
        info.setProxyNum(createdInvitCode(0, RoleCodes.ROLE_KEY_INTEGER.get(info.getRoleGrade())));
        info.setCreatorId(userInfo.getUuid());
        info.setCreatorName(userInfo.getUserName());
        info.setUpdatedTime(new Date());
        info.setUpdateUserId(userInfo.getUuid());
        info.setUpdateUserName(userInfo.getUserName());
        info = adminClientInfoRepository.save(info);
        AdminClientLoginInfo loginInfo = new AdminClientLoginInfo();
        loginInfo.setAdminClientId(info.getUuid());
        loginInfo.setLoginName(info.getUserName());
        loginInfo.setPwd(changePwd(pwd));
        loginInfo.setCreatedTime(new Date());
        loginInfo.setCreatorId(userInfo.getUuid());
        loginInfo.setCreatorName(userInfo.getUserName());
        loginInfo.setUpdatedTime(new Date());
        loginInfo.setUpdateUserId(userInfo.getUuid());
        loginInfo.setUpdateUserName(userInfo.getUserName());
        adminClientLoginInfoRepository.save(loginInfo);

        //差邀请页表没填
        ProxySpread spread = createdNewSpread(info);
        spread.setCreatedTime(new Date());
        spread.setCreatorId(userInfo.getUuid());
        spread.setCreatorName(userInfo.getUserName());
        spread.setUpdatedTime(new Date());
        spread.setUpdateUserId(userInfo.getUuid());
        spread.setUpdateUserName(userInfo.getUserName());
        spreadRepository.save(spread);

        if(proxyModel.getMonthCommission()==null||proxyModel.getMonthCommission()==0||
                proxyModel.getDayCommission()==null||proxyModel.getDayCommission()==0
                ||proxyModel.getBackCommission()==null||proxyModel.getBackCommission()==0){
            return ResultCode.COMMISSION_NULL_ERR;
        }else {
            ProxyCommission commission = new ProxyCommission();
            commission.setBackCommission(proxyModel.getBackCommission());
            commission.setDayCommission(proxyModel.getDayCommission());
            commission.setMonthCommission(proxyModel.getMonthCommission());
            commission.setSalemanId(info.getUuid());
            commission.setCreatedTime(new Date());
            commission.setCreatorId(userInfo.getUuid());
            commission.setCreatorName(userInfo.getUserName());
            commission.setUpdatedTime(new Date());
            commission.setUpdateUserId(userInfo.getUuid());
            commission.setUpdateUserName(userInfo.getUserName());
            commissionRepository.save(commission);
        }
        return "success";
    }


    //获取个人收佣信息
    public ProxyModel getCommissions(IdModel ids) throws ProxyException{
        ProxyModel proxyModel = new ProxyModel();
        ProxyCommission commission = commissionRepository.findCommissionBySalemanId(ids.getIds().get(0));

        if(commission!=null) {
            proxyModel.setBackCommission(commission.getBackCommission());
            proxyModel.setDayCommission(commission.getDayCommission());
            proxyModel.setMonthCommission(commission.getMonthCommission());
            proxyModel.setProxyId(commission.getSalemanId());
        }else {
            throw new ProxyException(ResultCode.SELECT_NULL_MSG);
        }
        return proxyModel;

    }


    //修改个人收佣信息
    public void updateCommission(UserTokenInfo userInfo,ProxyModel model) throws ProxyException{
        ProxyCommission proxyCommission = commissionRepository.findCommissionBySalemanId(model.getProxyId());
        proxyCommission.setBackCommission(model.getBackCommission());
        proxyCommission.setDayCommission(model.getDayCommission());
        proxyCommission.setMonthCommission(model.getMonthCommission());
        proxyCommission.setUpdatedTime(new Date());
        proxyCommission.setUpdateUserId(userInfo.getUuid());
        proxyCommission.setUpdateUserName(userInfo.getUserName());
        commissionRepository.save(proxyCommission);
       /* Map<String,Object> params = new HashMap<String,Object>();
        try {
            params.put("backCommission",model.getBackCommission());
            params.put("dayCommission",model.getDayCommission());
            params.put("monthCommission",model.getMonthCommission());
            params.put("updateId",userInfo.getUuid());
            params.put("updateName",userInfo.getUserName());
            params.put("updateTime",new Date());
            params.put("saleId",model.getProxyId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ProxyException(ResultCode.POWER_VISIT_ERR);
        }
        if( !(commissionMapper.updateCommission(params)>0)){
           throw new ProxyException(ResultCode.UPDATE_ERR);
       }*/
    }

    //获取员工信息列表
    public PageInfo<EmployeeModel> getEmployee(UserTokenInfo userInfo,EmployeeModel model){
        PageHelper.startPage(model.getPage(),model.getLimit());
        model.setId(userInfo.getUuid());
        List<EmployeeModel> employeeModels = adminClientInfoMapper.selectEmp(model);
        PageInfo<EmployeeModel> page = new PageInfo<EmployeeModel>(employeeModels);
        return page;
    }
    //获取邀请页面信息
    public PageInfo<SpreadModel> getSpreads(UserTokenInfo userInfo,SpreadModel spreadModel){
        PageHelper.startPage(spreadModel.getPageIndex(),spreadModel.getPageSize());
        spreadModel.setId(userInfo.getUuid());
        List<SpreadModel> spreadModels = new ArrayList<SpreadModel>();
        if(RoleCodes.ROLE_KEY_STRING.get("E")==spreadModel.getPosition()){
            spreadModel.setPosition(userInfo.getRoleGrade());
            spreadModels = spreadMapper.getSpreadEmp(spreadModel);
        } else if (RoleCodes.ROLE_KEY_STRING.get("A")==spreadModel.getPosition()){
            spreadModel.setPosition(userInfo.getRoleGrade());
            spreadModels = spreadMapper.getSpreadProxy(spreadModel);
        } else if (RoleCodes.ROLE_KEY_STRING.get("U")==spreadModel.getPosition()
                &&RoleCodes.ROLE_KEY_STRING.get("M")==userInfo.getRoleGrade()){
            spreadModels = spreadMapper.getSpreadUser(spreadModel);
        }
        PageInfo<SpreadModel> page = new PageInfo<>(spreadModels);
        return page;
    }




    //获取管理员树状图
    private List<ProxyModel> getTreeProxyModels(UserTokenInfo userInfo,List<ProxyModel> ps){
        List<ProxyModel> proxyModels = new ArrayList<ProxyModel>();
        ps.forEach(model -> {
            for (ProxyModel proxyModel:ps) {
                if (model.getProxyId().equals(proxyModel.getBossId())){
                    model.getProxyModels().add(proxyModel);
                }
            }
            if(RoleCodes.ROLE_KEY_STRING.get("A").equals(model.getRoleGrade())){
                proxyModels.add(model);
            }
        });
        return proxyModels;
    }


    //手动给管理员分页
    private PageInfo<ProxyModel> manageProxy(UserTokenInfo userInfo,ProxyModel proxyModel) throws ProxyException{
        List<ProxyModel> proxyModels = getTreeProxyModels(userInfo,adminClientInfoMapper.selectProxy(proxyModel));
        List<ProxyModel> list = new ArrayList<ProxyModel>();
        if((proxyModel.getPage()-1)*proxyModel.getLimit()>proxyModels.size()){
            throw new ProxyException(ResultCode.SELECT_OVER_MSG);
        }
        int j = proxyModels.size()>(proxyModel.getPage()-1)*proxyModel.getLimit()+proxyModel.getLimit()
                ?(proxyModel.getPage()-1)*proxyModel.getLimit()+proxyModel.getLimit():proxyModels.size();
        for (int i = (proxyModel.getPage()-1)*proxyModel.getLimit()
             ;i<j;i++){
            list.add(proxyModels.get(i));
        }
        PageHelper.startPage(proxyModel.getPage(),proxyModel.getLimit());
        PageInfo<ProxyModel> page = new PageInfo<ProxyModel>(list);
        page.setTotal(proxyModels.size());
        page.setPageSize(proxyModel.getLimit());
        page.setPageNum(proxyModel.getPage());
        return page;
    }

    //普通代理分页
    private PageInfo<ProxyModel> commonProxy(ProxyModel proxyModel){
        PageHelper.startPage(proxyModel.getPage(),proxyModel.getLimit());
        List<ProxyModel> list = adminClientInfoMapper.selectProxy(proxyModel);
        PageInfo<ProxyModel> page = new PageInfo<ProxyModel>(list);
        return page;
    }

    //生成新的邀请
    private ProxySpread createdNewSpread(AdminClientInfo info) throws ProxyException{
        ProxySpread spread = new ProxySpread();
        InputStream is = null;
        File file = null;
        ByteArrayOutputStream outputStream = null;
        try {
            spread.setSalemanId(info.getUuid());;
            spread.setInviteCode(info.getProxyNum());
            spread.setSpreadLink(getNewInviteLink(info.getProxyNum(),info.getProxyNum()));
            String path = getNewCodePic(spread.getSpreadLink(),info.getProxyNum());
            file = new File(path);
            is = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[100];
            int rc = 0;
            while ((rc=is.read(bytes,0,100))>0){
                outputStream.write(bytes,0,rc);
            }
            byte[] piccodes = outputStream.toByteArray();
            spread.setSpreadCodePic(piccodes);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ProxyException(ResultCode.INVITCODE_PIC_MSG);
        } finally {
            try {
                outputStream.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(file.exists()&&file.isFile()){
                file.delete();
            }
        }
        return spread;
    }

    //递归生成不重复的邀请码，不超过5次
    private String createdInvitCode(Integer time,String positionName) throws  ProxyException{
        if(!(time<5)){
            throw new ProxyException(ResultCode.INVITCODE_GET_MSG);
        }
        time++;
        StringBuffer sb = new StringBuffer(positionName);
        sb.append((int)((Math.random()*9+1)*10000));
        String invitCode = sb.toString();
        if(spreadMapper.countInvit(invitCode)>0){
            invitCode = createdInvitCode(time,positionName);
        }
        return invitCode;
    }

    //生成新的邀请链接
    private String getNewInviteLink(String invite,String proxyNum){
        StringBuffer sb = new StringBuffer(inviteLink);
        sb.append("?inviteCode=");
        sb.append(invite);
        sb.append("&proxyNum=");
        sb.append(proxyNum);
        return sb.toString();
    }

    //生成二维码
    private String getNewCodePic(String invite,String code) throws ProxyException{// 二维码内容
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
        BitMatrix bitMatrix = null;
        File filePath = new File(picSavePath);
        if(!filePath.exists()){
            filePath.mkdir();
        }
        String path = picSavePath+File.separator+ code+"."+picFormat;
        try {
            bitMatrix = new MultiFormatWriter().encode(invite,
                    BarcodeFormat.QR_CODE, codeWidth, codeHeight, hints);
        // 生成二维码
        File outputFile = new File(path);
        MatrixToImageWriter.writeToFile(bitMatrix, picFormat, outputFile);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ProxyException(ResultCode.INVITCODE_PIC_MSG);
        }
        return path;
    }

    private String changePwd(String pwd){
        return CommonUtil.toSha256(pwd);
    }



    //通过邀请码获取邀请人id
    public String getInviteId(String inviteCode){
        if (!StringUtils.isEmpty(inviteCode)){
            return spreadMapper.getSpreadId(inviteCode).getId();
        }
        return "";
    }


    public void saveUserSpread(UserTokenInfo userInfo) throws ProxyException{
        ProxySpread proxySpread = new ProxySpread();
        InputStream is = null;
        File file = null;
        ByteArrayOutputStream outputStream = null;
        try {
            proxySpread.setInviteCode(createdInvitCode(0, RoleCodes.ROLE_KEY_INTEGER.get(userInfo.getRoleGrade())));
            proxySpread.setSalemanId(userInfo.getUuid());
            proxySpread.setSpreadLink(getNewInviteLink(proxySpread.getInviteCode(),proxySpread.getInviteCode()));
            String path = getNewCodePic(proxySpread.getSpreadLink(),proxySpread.getInviteCode());
            file = new File(path);
            is = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[100];
            int rc = 0;
            while ((rc=is.read(bytes,0,100))>0){
                outputStream.write(bytes,0,rc);
            }
            byte[] piccodes = outputStream.toByteArray();
            proxySpread.setSpreadCodePic(piccodes);
            spreadRepository.save(proxySpread);
        } catch (IOException e) {
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ProxyException(e.getMessage());
        }finally {
            try {
                outputStream.close();
                is.close();
            } catch (IOException e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new ProxyException(e.getMessage());
            }
            if(file.exists()&&file.isFile()){
                file.delete();
            }
        }

    }


    //获取个人推广页面
    public SpreadModel getOwnSpread(String userId){
        return spreadMapper.getOwnSpread(userId);
    }
}
