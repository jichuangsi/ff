package cn.com.fintheircing.admin.system.service;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.common.utils.CommonUtil;
import cn.com.fintheircing.admin.system.dao.mapper.ISystemBrandMapper;
import cn.com.fintheircing.admin.system.dao.mapper.ISystemHolidayMapper;
import cn.com.fintheircing.admin.system.dao.repository.ISystemBrandRepository;
import cn.com.fintheircing.admin.system.dao.repository.ISystemHolidayRepository;
import cn.com.fintheircing.admin.system.entity.SystemBrand;
import cn.com.fintheircing.admin.system.entity.SystemHoliday;
import cn.com.fintheircing.admin.system.exception.SystemException;
import cn.com.fintheircing.admin.system.model.brand.BrandModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidayModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidaySearchModel;
import cn.com.fintheircing.admin.system.utils.MappingModel2EntityConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

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

    private Logger logger = LoggerFactory.getLogger(getClass());

    //保存假期
    public void saveHoliday(UserTokenInfo userInfo, HolidayModel model) throws SystemException{
        SystemHoliday newHoliday = new SystemHoliday();

        newHoliday.setBeginTime(CommonUtil.getlongTime(model.getStart()+hms,sdformat));
        newHoliday.setEndTime(CommonUtil.getlongTime(model.getEnd()+hms,sdformat));
        if(newHoliday.getBeginTime()>=newHoliday.getEndTime()){
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
    public void updateHoliday(UserTokenInfo userInfo,HolidayModel model) throws SystemException{
        SystemHoliday systemHoliday = systemHolidayRepository.findByUuid(model.getId());
        if (systemHoliday!=null) {
            if (SystemHoliday.STATUS_ACTIVE.equals(model.getStatus())
                    || SystemHoliday.STATUS_DISABLED.equals(model.getStatus())) {
                systemHoliday.setStatus(model.getStatus());
            } else {
                throw new SystemException(ResultCode.VISIT_VALIDITY_MSG);
            }
            systemHoliday.setBeginTime(CommonUtil.getlongTime(model.getStart() + hms, sdformat));
            systemHoliday.setEndTime(CommonUtil.getlongTime(model.getEnd() + hms, sdformat));
            if (systemHoliday.getBeginTime() >= systemHoliday.getEndTime()) {
                throw new SystemException(ResultCode.DATE_INVIDATE_MSG);
            }
            systemHoliday.setRemarks(model.getRemarks());
            systemHoliday.setUpdatedTime(new Date());
            systemHoliday.setUpdateUserId(userInfo.getUuid());
            systemHoliday.setUpdateUserName(userInfo.getUserName());
            systemHolidayRepository.save(systemHoliday);
        }else{
            throw new SystemException(ResultCode.SELECT_NULL_MSG);
        }
    }

    //批量删除假期
    public Boolean deleteHolidates(UserTokenInfo userInfo, IdModel ids){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("name",userInfo.getUserName());
        params.put("id",userInfo.getUuid());
        params.put("time",new Date());
        params.put("list",ids.getIds());
        return systemHolidayMapper.deleteHolidays(params)>0;
    }


    //假期分页
    public PageInfo<HolidayModel> getPageHolidays(HolidaySearchModel model) throws SystemException{
        PageHelper.startPage(model.getPageIndex(),model.getPageSize());
        if (!StringUtils.isEmpty(model.getSearchTime())){
            model.setLongtime(CommonUtil.getlongTime(model.getSearchTime()+hms,sdformat));
        } else {
            model.setLongtime(0);
        }
        List<HolidayModel> holidayModels = systemHolidayMapper.selectHolidays(model);
        PageInfo<HolidayModel> pageInfo = new PageInfo<HolidayModel>(holidayModels);
        return pageInfo;
    }



    //保存轮播图
    public void saveBrand(UserTokenInfo userInfo, BrandModel model,MultipartFile file) throws SystemException{
        SystemBrand systemBrand = MappingModel2EntityConverter.CONVERTERFORBRANDMODEL(userInfo,model);
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
    public Boolean deleteBrands(UserTokenInfo userInfo,IdModel ids){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("name",userInfo.getUserName());
        params.put("id",userInfo.getUuid());
        params.put("time",new Date());
        params.put("list",ids.getIds());
        return systemBrandMapper.deleteBrands(params)>0;
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

   public void updateBrand(MultipartFile file ,BrandModel model,UserTokenInfo userInfo) throws SystemException{
       SystemBrand systemBrand = systemBrandRepository.findByUuid(model.getUuid());
       if(systemBrand!=null) {
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
       }else {
           throw new SystemException(ResultCode.SELECT_NULL_MSG);
       }
   }

   public List<BrandModel> getBrands(){
       List<BrandModel> brandModels = systemBrandMapper.selectBrand();
       brandModels.forEach(brandMode->{
           brandMode.setCreatedTime(brandMode.getBeiginTime().getTime());
           brandMode.setUpdateTime(brandMode.getModifyTime().getTime());
       });
       return brandModels;
   }
}
