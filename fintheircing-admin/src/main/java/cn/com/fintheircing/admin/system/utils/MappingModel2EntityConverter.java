package cn.com.fintheircing.admin.system.utils;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.system.entity.SystemBrand;
import cn.com.fintheircing.admin.system.entity.SystemHoliday;
import cn.com.fintheircing.admin.system.exception.SystemException;
import cn.com.fintheircing.admin.system.model.brand.BrandModel;
import cn.com.fintheircing.admin.system.model.holiday.HolidayModel;

import java.util.Date;

public final class MappingModel2EntityConverter {
    private MappingModel2EntityConverter(){}

    public final static SystemHoliday
    CONVERTERFORHOLIDAYMODEL(UserTokenInfo userInfo, HolidayModel model) throws SystemException{
        SystemHoliday systemHoliday = new SystemHoliday();
        if(SystemHoliday.STATUS_ACTIVE.equals(model.getStatus())
                ||SystemHoliday.STATUS_DISABLED.equals(model.getStatus())){
            systemHoliday.setStatus(model.getStatus());
        }else {
            throw new SystemException(ResultCode.VISIT_VALIDITY_MSG);
        }
        systemHoliday.setUpdateUserName(userInfo.getUserName());
        systemHoliday.setUpdateUserId(userInfo.getUuid());
        systemHoliday.setUpdatedTime(new Date());

        if(model.getCreatedTime()==0){
            systemHoliday.setCreatorName(userInfo.getUserName());
            systemHoliday.setCreatorId(userInfo.getUuid());
            systemHoliday.setCreatedTime(new Date());
        }

        systemHoliday.setRemarks(model.getRemarks());
        systemHoliday.setUuid(model.getId());
        return systemHoliday;
    }

    public static final SystemBrand CONVERTERFORBRANDMODEL(UserTokenInfo userInfo, BrandModel model) throws SystemException{
        SystemBrand systemBrand = new SystemBrand();
        if (SystemBrand.APPLYON_PC.equals(model.getApplyOn())
                ||SystemBrand.APPLYON_APP.equals(model.getApplyOn())
               /* &&(SystemBrand.STATUS_ACTIVE.equals(model.getStatus())
                ||SystemBrand.STATUS_DISABLED.equals(model.getStatus()))*/){
            systemBrand.setApplyOn(model.getApplyOn());
  /*          systemBrand.setStatus(model.getStatus());*/
        }else {
            throw new SystemException(ResultCode.VISIT_VALIDITY_MSG);
        }
        systemBrand.setBrandName(model.getName());
        systemBrand.setUuid(model.getUuid());
        if (model.getCreatedTime()==0) {
            systemBrand.setCreatedTime(new Date());
            systemBrand.setCreatorId(userInfo.getUuid());
            systemBrand.setCreatorName(userInfo.getUserName());
        }
        systemBrand.setUpdatedTime(new Date());
        systemBrand.setUpdateUserId(userInfo.getUuid());
        systemBrand.setUpdateUserName(userInfo.getUserName());

        return systemBrand;
    }
}
