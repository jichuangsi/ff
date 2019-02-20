package cn.com.fintheircing.admin.taxation.service;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.taxation.dao.mapper.IBusinessTaxationMapper;
import cn.com.fintheircing.admin.taxation.dao.repository.IBusinessTaxationRepository;
import cn.com.fintheircing.admin.taxation.entity.BusinessTaxation;
import cn.com.fintheircing.admin.taxation.exception.TaxationException;
import cn.com.fintheircing.admin.taxation.model.TaxationModel;
import cn.com.fintheircing.admin.taxation.utils.MappingEntity2ModelConverter;
import cn.com.fintheircing.admin.taxation.utils.MappingModel2EntityConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TaxationService {

    @Resource
    private IBusinessTaxationRepository businessTaxationRepository;
    @Resource
    private IBusinessTaxationMapper businessTaxationMapper;

    //保存新税收
    public void saveNewTaxation(UserTokenInfo userInfo, TaxationModel model) throws TaxationException {
        BusinessTaxation taxation = MappingModel2EntityConverter.CONVERTERFORTAXATIONMODEL(model);
        taxation.setCreatedTime(new Date());
        taxation.setCreatorId(userInfo.getUuid());
        taxation.setCreatorName(userInfo.getUserName());
        taxation.setUpdateUserId(userInfo.getUuid());
        taxation.setUpdateUserName(userInfo.getUserName());
        taxation.setLabelName(createdNewLabelName(0));
        businessTaxationRepository.save(taxation);
    }

    //生成新的行名
    private String createdNewLabelName(int i) throws TaxationException {
        if (i == 5) {
            throw new TaxationException(ResultCode.TAX_CREATED_ERR);
        }
        StringBuffer stringBuffer = new StringBuffer("tax");
        stringBuffer.append((int) ((Math.random() * 9 + 1) * 10000));
        String result = stringBuffer.toString();
        if (businessTaxationRepository.countByLabelName(result) > 0) {
            createdNewLabelName(i + 1);
        }
        return result;
    }

    //批量删除
    public void deleteTaxation(UserTokenInfo userInfo, IdModel ids) throws TaxationException {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", userInfo.getUuid());
        param.put("name", userInfo.getUserName());
        param.put("time", new Date());
        param.put("list", ids.getIds());
        if (!(businessTaxationMapper.deleteTaxations(param) > 0)) {
            throw new TaxationException(ResultCode.TAX_DELETE_ERR);
        }
    }

    //获取所有taxation
    public List<TaxationModel> getTaxations(){
       List<BusinessTaxation> businessTaxations = businessTaxationRepository.findByDeleteFlag("0");
        return  changeTaxations(businessTaxations);
    }

    //转换
    private List<TaxationModel> changeTaxations(List<BusinessTaxation> businessTaxations){
        List<TaxationModel> taxationModels = new ArrayList<TaxationModel>();
        businessTaxations.forEach(taxation -> {
            taxationModels.add(MappingEntity2ModelConverter.CONVERTERFORMBUSINESSTAXATION(taxation));
        });
        return taxationModels;
    }

    //修改税收，除税率外
    public void updateTaxation(UserTokenInfo userInfo,TaxationModel model) throws TaxationException{
        if (null != model.getTaxationRate()){
            throw new TaxationException(ResultCode.TAX_UPDATERATE_ERR);
        }
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("userName",userInfo.getUserName());
        params.put("userId",userInfo.getUuid());
        params.put("time",new Date());
        params.put("taxName",model.getTaxationName());
        params.put("remark",model.getRemarks());
        params.put("businessTo",model.getBusinessTo());
        params.put("id",model.getId());
        if (!(businessTaxationMapper.updateTaxation(params)>0)){
            throw new TaxationException(ResultCode.UPDATE_ERR_MSG);
        }
    }
}
