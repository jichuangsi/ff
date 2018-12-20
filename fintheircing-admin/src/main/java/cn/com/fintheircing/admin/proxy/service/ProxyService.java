package cn.com.fintheircing.admin.proxy.service;

import cn.com.fintheircing.admin.common.constant.PositionCode;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.MatrixToImageWriter;
import cn.com.fintheircing.admin.login.entity.AdminClientLoginInfo;
import cn.com.fintheircing.admin.proxy.dao.mapper.IAdminClientInfoMapper;
import cn.com.fintheircing.admin.proxy.dao.mapper.ICommissionMapper;
import cn.com.fintheircing.admin.proxy.dao.mapper.ISpreadMapper;
import cn.com.fintheircing.admin.proxy.dao.repository.IAdminClientInfoRepository;
import cn.com.fintheircing.admin.proxy.dao.repository.IAdminClientLoginInfoRepository;
import cn.com.fintheircing.admin.proxy.dao.repository.ICommissionRepository;
import cn.com.fintheircing.admin.proxy.entity.Commission;
import cn.com.fintheircing.admin.proxy.entity.Spread;
import cn.com.fintheircing.admin.proxy.exception.ProxyException;
import cn.com.fintheircing.admin.proxy.model.EmployeeModel;
import cn.com.fintheircing.admin.proxy.model.IdModel;
import cn.com.fintheircing.admin.proxy.model.ProxyModel;
import cn.com.fintheircing.admin.proxy.model.SpreadModel;
import cn.com.fintheircing.admin.proxy.utils.MappingModel2EntityConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

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

    //获取代理信息列表
    public PageInfo<ProxyModel> getProxyList(UserTokenInfo userInfo , ProxyModel proxyModel){
        proxyModel.setProxyId(userInfo.getUuid());
        PageInfo<ProxyModel> pageInfo = new PageInfo<ProxyModel>();
        if(PositionCode.POSITION_MANAGE.getIndex().equals(userInfo.getPosition())){
            pageInfo = manageProxy(userInfo,proxyModel);
        }else {
            pageInfo = commonProxy(proxyModel);
        }
        return pageInfo;
    }

    //保存代理或员工
    @Transactional(rollbackFor=Exception.class)
    public void saveProxy(UserTokenInfo adminLoginModel , ProxyModel proxyModel) throws ProxyException{
        AdminClientInfo info = MappingModel2EntityConverter.CONVERTERFORPROXYMODEL(proxyModel);
        info.setRole(AdminClientInfo.ROLE_ADMIN);
        info.setBossId(adminLoginModel.getUuid());
        if(proxyModel.getProxyPosition()==null) {
            if (adminLoginModel.getPosition() < PositionCode.POSITION_PROXY_TWO.getIndex()) {
                info.setPosition(adminLoginModel.getPosition() + 1);
            } else {
                throw new ProxyException(ResultCode.POWER_VISIT_ERR);
            }
        } else{
            info.setPosition(proxyModel.getProxyPosition());
        }
        info.setStatus(AdminClientInfo.STATUS_EXIST);
        info = adminClientInfoRepository.save(info);
        AdminClientLoginInfo loginInfo = new AdminClientLoginInfo();
        loginInfo.setAdminClientId(info.getUuid());
        loginInfo.setLoginName(info.getUserName());
        loginInfo.setPwd(pwd);
        adminClientLoginInfoRepository.save(loginInfo);
        if(StringUtils.isEmpty(proxyModel.getMonthCommission())||StringUtils.isEmpty(proxyModel.getDayCommission())
                ||StringUtils.isEmpty(proxyModel.getBackCommission())){
            throw new ProxyException(ResultCode.COMMISSION_NULL_ERR);
        }
        Commission commission = new Commission();
        commission.setBackCommission(proxyModel.getBackCommission());
        commission.setDayCommission(proxyModel.getDayCommission());
        commission.setMonthCommission(proxyModel.getMonthCommission());
        commission.setSalemanId(info.getUuid());
        commissionRepository.save(commission);
        //差邀请页表没填
    }


    //获取个人收佣信息
    public ProxyModel getCommissions(IdModel ids){
        ProxyModel proxyModel = new ProxyModel();
        Commission commission = commissionRepository.findCommissionBySalemanId(ids.getIds().get(0));

        proxyModel.setBackCommission(commission.getBackCommission());
        proxyModel.setDayCommission(commission.getDayCommission());
        proxyModel.setMonthCommission(commission.getMonthCommission());
        proxyModel.setProxyId(commission.getSalemanId());
        return proxyModel;

    }


    //修改个人收佣信息
    public void updateCommission(ProxyModel model) throws ProxyException{
       if( !(commissionMapper.updateCommission(model)>0)){
           throw new ProxyException(ResultCode.UPDATE_ERR);
       }
    }

    //获取员工信息列表
    public PageInfo<EmployeeModel> getEmployee(EmployeeModel model){
        PageHelper.startPage(model.getPageIndex(),model.getPageSize());
        List<EmployeeModel> employeeModels = adminClientInfoMapper.selectEmp(model);
        PageInfo<EmployeeModel> page = new PageInfo<EmployeeModel>(employeeModels);
        return page;
    }
    //获取邀请页面信息
    public PageInfo<SpreadModel> getSpreaads(UserTokenInfo userInfo,SpreadModel spreadModel){
        PageHelper.startPage(spreadModel.getPageIndex(),spreadModel.getPageSize());
        List<SpreadModel> spreadModels = new ArrayList<>();
        if(PositionCode.POSITION_EMP.getIndex().equals(spreadModel.getPosition())){
            spreadModels = null;//获取员工的列表
        }else{
            spreadModels = null;//获取代理的列表
        }
        PageInfo<SpreadModel> page = new PageInfo<>(spreadModels);
        return page;
    }




    //获取管理员树状图
    private List<ProxyModel> getTreeProxyModels(UserTokenInfo userInfo,List<ProxyModel> ps){
        List<ProxyModel> proxyModels = new ArrayList<ProxyModel>();
        ps.forEach(model -> {
            if(userInfo.getUuid().equals(model.getBossId())){
                for (ProxyModel proxyModel:ps) {
                    if (model.getProxyId().equals(proxyModel.getBossId())){
                        model.getProxyModels().add(proxyModel);
                    }
                }
                proxyModels.add(model);
            }
        });
        return proxyModels;
    }


    //手动给管理员分页
    private PageInfo<ProxyModel> manageProxy(UserTokenInfo userInfo,ProxyModel proxyModel){
        List<ProxyModel> proxyModels = getTreeProxyModels(userInfo,adminClientInfoMapper.selectProxy(proxyModel));
        PageInfo<ProxyModel> page = new PageInfo<>();
        page.setPageNum(proxyModel.getPageIndex());
        page.setPageSize(proxyModel.getPageSize());
        page.setTotal(proxyModels.size());
        List<ProxyModel> list = new ArrayList<ProxyModel>();
        for (int i = (proxyModel.getPageIndex()-1)*proxyModel.getPageSize()
             ;i<(proxyModel.getPageIndex()-1)*proxyModel.getPageSize()+proxyModel.getPageSize();i++){
            list.add(proxyModels.get(i));
        }
        page.setList(list);
        return page;
    }

    //普通代理分页
    private PageInfo<ProxyModel> commonProxy(ProxyModel proxyModel){
        PageHelper.startPage(proxyModel.getPageIndex(),proxyModel.getPageSize());
        List<ProxyModel> list = adminClientInfoMapper.selectProxy(proxyModel);
        PageInfo<ProxyModel> page = new PageInfo<ProxyModel>(list);
        return page;
    }

    //生成新的邀请
    private Spread createdNewSpread(AdminClientInfo info) throws ProxyException{
        Spread spread = new Spread();
        spread.setSalemanId(info.getUuid());
        String invitCode = createdInvitCode(0,PositionCode.getName(info.getPosition()));
        spread.setInviteCode(invitCode);
        spread.setSpreadLink(getNewInviteLink(invitCode,info.getProxyNum()));
        String path = getNewCodePic(spread.getSpreadLink(),invitCode);

        return spread;
    }

    //递归生成不重复的邀请码，不超过5次
    private String createdInvitCode(Integer time,String positionName) throws  ProxyException{
        if(!(time<5)){
            throw new ProxyException(ResultCode.INVITCODE_GET_MSG);
        }
        time++;
        StringBuffer sb = new StringBuffer(positionName);
        for(int i =0;i<5;i++){
            sb.append(new Random(new Date().getTime()).nextInt(10));
        }
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
        return invite;
    }

    //生成二维码
    private String getNewCodePic(String invite,String code) throws ProxyException{// 二维码内容
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
        BitMatrix bitMatrix = null;
        String path = picSavePath+File.separator+ code+"."+picFormat;
        try {
            bitMatrix = new MultiFormatWriter().encode(invite,
                    BarcodeFormat.QR_CODE, codeWidth, codeHeight, hints);
        // 生成二维码
        File outputFile = new File(path);
        MatrixToImageWriter.writeToFile(bitMatrix, picFormat, outputFile);
        } catch (Exception e) {
            throw new ProxyException(ResultCode.INVITCODE_PIC_MSG);
        }
        return path;
    }
}
