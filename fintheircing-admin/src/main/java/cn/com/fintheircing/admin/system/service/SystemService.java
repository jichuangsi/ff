package cn.com.fintheircing.admin.system.service;

import cn.com.fintheircing.admin.account.entity.AdminRole;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.RoleModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.system.dao.mapper.IBlackListMapper;
import cn.com.fintheircing.admin.system.dao.repository.IBlackListRepository;
import cn.com.fintheircing.admin.system.dao.mapper.IAdminRoleMapper;
import cn.com.fintheircing.admin.system.dao.mapper.ISystemBrandMapper;
import cn.com.fintheircing.admin.system.dao.mapper.ISystemHolidayMapper;
import cn.com.fintheircing.admin.system.dao.repository.IAdminRoleRepository;
import cn.com.fintheircing.admin.system.dao.repository.ISystemBrandRepository;
import cn.com.fintheircing.admin.system.dao.repository.ISystemHolidayRepository;
import cn.com.fintheircing.admin.system.entity.SystemBlackList;
import cn.com.fintheircing.admin.system.entity.SystemBrand;
import cn.com.fintheircing.admin.system.entity.SystemHoliday;
import cn.com.fintheircing.admin.system.exception.SystemException;
import cn.com.fintheircing.admin.system.model.black.BlackModel;
import cn.com.fintheircing.admin.system.model.brand.BrandModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidayModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidaySearchModel;
import cn.com.fintheircing.admin.useritem.utils.MappingModel2EntityConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemService {

    @Value("${custom.system.sdformat}")
    private String sdformat;
    @Value("${custom.system.hms}")
    private String hms;

    @Resource
    private ISystemHolidayRepository systemHolidayRepository;
    @Resource
    private ISystemHolidayMapper systemHolidayMapper;
    @Resource
    private ISystemBrandRepository systemBrandRepository;
    @Resource
    private ISystemBrandMapper systemBrandMapper;
    @Resource
    private IAdminRoleMapper adminRoleMapper;
    @Resource
    private IAdminRoleRepository adminRoleRepository;
    @Resource
    private IBlackListRepository blackListRepository;
    @Resource
    private IBlackListMapper blackListMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //保存假期
    public void saveHoliday(UserTokenInfo userInfo, HolidayModel model) throws SystemException {
        SystemHoliday newHoliday = new SystemHoliday();

        newHoliday.setBeginTime(CommonUtil.getlongTime(model.getStart() + hms, sdformat));
        newHoliday.setEndTime(CommonUtil.getEndLongTime(model.getEnd() + hms, sdformat));
        if (newHoliday.getBeginTime() >= newHoliday.getEndTime()) {
            throw new SystemException(ResultCode.DATE_INVIDATE_MSG);
        }

        newHoliday.setRemarks(model.getRemarks());
        newHoliday.setStatus(SystemHoliday.STATUS_ACTIVE);

        newHoliday.setCreatedTime(new Date());
        newHoliday.setCreatorId(userInfo.getUuid());
        newHoliday.setCreatorName(userInfo.getUserName());
        newHoliday.setUpdatedTime(new Date());
        newHoliday.setUpdateUserId(userInfo.getUuid());
        newHoliday.setUpdateUserName(userInfo.getUserName());
        systemHolidayRepository.save(newHoliday);
    }

    //修改假期
    public void updateHoliday(UserTokenInfo userInfo, HolidayModel model) throws SystemException {
        SystemHoliday systemHoliday = systemHolidayRepository.findByUuid(model.getId());
        if (systemHoliday != null) {
            if (SystemHoliday.STATUS_ACTIVE.equals(model.getStatus())
                    || SystemHoliday.STATUS_DISABLED.equals(model.getStatus())) {
                systemHoliday.setStatus(model.getStatus());
            } else {
                throw new SystemException(ResultCode.VISIT_VALIDITY_MSG);
            }
            systemHoliday.setBeginTime(CommonUtil.getlongTime(model.getStart() + hms, sdformat));
            systemHoliday.setEndTime(CommonUtil.getEndLongTime(model.getEnd() + hms, sdformat));
            if (systemHoliday.getBeginTime() >= systemHoliday.getEndTime()) {
                throw new SystemException(ResultCode.DATE_INVIDATE_MSG);
            }
            systemHoliday.setRemarks(model.getRemarks());
            systemHoliday.setUpdatedTime(new Date());
            systemHoliday.setUpdateUserId(userInfo.getUuid());
            systemHoliday.setUpdateUserName(userInfo.getUserName());
            systemHolidayRepository.save(systemHoliday);
        } else {
            throw new SystemException(ResultCode.SELECT_NULL_MSG);
        }
    }

    //批量删除假期
    public Boolean deleteHolidates(UserTokenInfo userInfo, IdModel ids) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", userInfo.getUserName());
        params.put("id", userInfo.getUuid());
        params.put("time", new Date());
        params.put("list", ids.getIds());
        return systemHolidayMapper.deleteHolidays(params) > 0;
    }

    //假期分页
    public PageInfo<HolidayModel> getPageHolidays(HolidaySearchModel model) throws SystemException {
        PageHelper.startPage(model.getPage(), model.getLimit());
        if (!StringUtils.isEmpty(model.getSearchTime())) {
            model.setLongtime(CommonUtil.getlongTime(model.getSearchTime() + hms, sdformat));
        } else {
            model.setLongtime(0);
        }
        List<HolidayModel> holidayModels = systemHolidayMapper.selectHolidays(model);
        PageInfo<HolidayModel> pageInfo = new PageInfo<HolidayModel>(holidayModels);
        return pageInfo;
    }

    //保存轮播图
    public void saveBrand(UserTokenInfo userInfo, BrandModel model, MultipartFile file) throws SystemException {
        SystemBrand systemBrand = MappingModel2EntityConverter.CONVERTERFORBRANDMODEL(userInfo, model);
        try {
            /* systemBrand.setStatus(SystemBrand.STATUS_ACTIVE);*/
            systemBrand.setContent(file.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new SystemException(ResultCode.PIC_UPLODE_MSG);
        }
        systemBrandRepository.save(systemBrand);
    }

    //删除轮播图
    public Boolean deleteBrands(UserTokenInfo userInfo, IdModel ids) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", userInfo.getUserName());
        params.put("id", userInfo.getUuid());
        params.put("time", new Date());
        params.put("list", ids.getIds());
        return systemBrandMapper.deleteBrands(params) > 0;
    }

   /* //修改轮播图不修改图片，仅修改其他字段
    public void updateBrand(UserTokenInfo userInfo,BrandModel model) throws SystemException{
        SystemBrand systemBrand = systemBrandRepository.findByUuid(model.getUuid());
        if ((SystemBrand.APPLYON_PC.equals(model.getApplyOn())
                ||SystemBrand.APPLYON_APP.equals(model.getApplyOn()))
                &&(SystemBrand.STATUS_ACTIVE.equals(model.getStatus())
                ||SystemBrand.STATUS_DISABLED.equals(model.getStatus()))){
            systemBrand.setApplyOn(model.getApplyOn());
            systemBrand.setStatus(model.getStatus());
        }else {
            throw new SystemException(ResultCode.VISIT_VALIDITY_MSG);
        }
        systemBrand.setBrandName(model.getName());
        systemBrand.setUpdateUserName(userInfo.getUserName());
        systemBrand.setUpdateUserId(userInfo.getUuid());
        systemBrand.setUpdatedTime(new Date());
        systemBrandRepository.save(systemBrand);
    }*/

    //修改轮播图
    public void updateBrand(MultipartFile file, BrandModel model, UserTokenInfo userInfo) throws SystemException {
        SystemBrand systemBrand = systemBrandRepository.findByUuid(model.getUuid());
        if (systemBrand != null) {
            try {
                systemBrand.setContent(file.getBytes());
                systemBrand.setUpdatedTime(new Date());
                systemBrand.setUpdateUserId(userInfo.getUuid());
                systemBrand.setUpdateUserName(userInfo.getUserName());
                systemBrandRepository.save(systemBrand);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new SystemException(ResultCode.PIC_UPLODE_MSG);
            }
        } else {
            throw new SystemException(ResultCode.SELECT_NULL_MSG);
        }
    }

    //列表轮播图
    public List<BrandModel> getBrands() {
        List<BrandModel> brandModels = systemBrandMapper.selectBrand();
        brandModels.forEach(brandMode -> {
            brandMode.setCreatedTime(brandMode.getBeiginTime().getTime());
            brandMode.setUpdateTime(brandMode.getModifyTime().getTime());
        });
        return brandModels;
    }

    //保存角色
    public void saveRoles() {
        if (!isExistRoles()) {
            AdminRole master = new AdminRole();
            master.setRoleGrade(0);
            master.setRoleName("系统管理员");
            master.setRoleSign("M");
            saveAdminRole(master);
            AdminRole proxyOne = new AdminRole();
            proxyOne.setRoleGrade(1);
            proxyOne.setRoleName("一级代理商");
            proxyOne.setRoleSign("A");
            saveAdminRole(proxyOne);
            AdminRole proxyTwo = new AdminRole();
            proxyTwo.setRoleGrade(2);
            proxyTwo.setRoleName("二级代理商");
            proxyTwo.setRoleSign("S");
            saveAdminRole(proxyTwo);
            AdminRole employee = new AdminRole();
            employee.setRoleGrade(3);
            employee.setRoleName("员工");
            employee.setRoleSign("E");
            saveAdminRole(employee);
            AdminRole risk = new AdminRole();
            risk.setRoleGrade(4);
            risk.setRoleName("风控");
            risk.setRoleSign("R");
            saveAdminRole(risk);
            AdminRole finance = new AdminRole();
            finance.setRoleGrade(5);
            finance.setRoleName("财务");
            finance.setRoleSign("F");
            saveAdminRole(finance);
            AdminRole user = new AdminRole();
            user.setRoleGrade(6);
            user.setRoleName("用户");
            user.setRoleSign("U");
            saveAdminRole(user);
        }
    }

    //是否存在角色
    @Cacheable(value = "isExistRoles")
    public Boolean isExistRoles() {
        return adminRoleMapper.selectCountAll() > 0;
    }

    //保存角色
    private void saveAdminRole(AdminRole adminRole) {
        adminRole.setCreatedTime(new Date());
        adminRole.setUpdatedTime(new Date());
        adminRoleRepository.save(adminRole);
    }

    //获取角色
    @Cacheable(value = "roles")
    public List<RoleModel> getRoles() {
        return adminRoleMapper.selectAllRole();
    }

    //是否在假期内
    public boolean isInHoliday(long nowTime) {
        return systemHolidayMapper.countInHoliday(nowTime) > 0;
    }

    //查是否存在黑名单
    public Boolean isExistBlackList(String ip) {
        List<SystemBlackList> systemBlackLists = getBlackList();
        for (SystemBlackList s : systemBlackLists) {
            if (s.getIpAddress().trim().equals(ip.trim())) {
                return true;
            }
        }
        return false;
    }

    @CacheEvict(value = "black_list")
    public List<SystemBlackList> getBlackList() {
        return blackListRepository.findByDeleteFlag("0");
    }

    //保存黑名单
    @CacheEvict(value = "black_list",allEntries = true)
    public void saveBlack(UserTokenInfo userInfo,BlackModel model) throws SystemException{
        if (isExistBlackList(model.getIpAdress())){
            throw new SystemException(ResultCode.BLACK_ALREAD_EXIST);
        }
        SystemBlackList systemBlackList = new SystemBlackList();
        systemBlackList.setCause(model.getRemarks());
        systemBlackList.setIpAddress(model.getIpAdress());
        systemBlackList.setCreatorId(userInfo.getUuid());
        systemBlackList.setCreatedTime(new Date());
        systemBlackList.setCreatorName(userInfo.getUserName());
        systemBlackList.setUpdateUserId(userInfo.getUuid());
        systemBlackList.setUpdateUserName(userInfo.getUserName());
        blackListRepository.save(systemBlackList);
    }

    //分页获取黑名单
    public PageInfo<BlackModel> getPageBlack(int index,int size){
        PageHelper.startPage(index,size);
        List<BlackModel> blackModels = blackListMapper.getPageBlack();
        PageInfo<BlackModel> pageInfo = new PageInfo<BlackModel>(blackModels);
        return pageInfo;
    }

    //移除黑名单
    public void deleteBlack(UserTokenInfo userInfo,String id) throws SystemException{
        SystemBlackList systemBlackList = blackListRepository.findByUuid(id);
        if (null == systemBlackList){
            throw  new SystemException(ResultCode.SELECT_NULL_MSG);
        }
        systemBlackList.setUpdateUserName(userInfo.getUserName());
        systemBlackList.setUpdateUserId(userInfo.getUuid());
        systemBlackList.setDeleteFlag("1");
        blackListRepository.save(systemBlackList);
    }
}
