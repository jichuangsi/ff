package cn.com.fintheircing.admin.risk.service;

import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.risk.exception.RiskException;
import cn.com.fintheircing.admin.risk.model.DangerousStockModel;
import cn.com.fintheircing.admin.risk.model.RiskContractModel;
import cn.com.fintheircing.admin.risk.model.RiskControlModel;
import cn.com.fintheircing.admin.taxation.exception.TaxationException;
import cn.com.fintheircing.admin.useritem.exception.TransactionSummaryException;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RiskService {

    @Resource
    private BusinessService businessService;
    @Resource
    private ItemService itemService;

    //一键强行平仓，不做任何判断
    public void oneKeyAbort(IdModel idModel) throws TaxationException{
        List<String> idList = idModel.getIds();
        for (String id:idList){
            try {
                businessService.abortRepository(id);
            } catch (Exception e) {
                throw new TaxationException(e.getMessage());
            }
        }
    }

    //获取分页风控合约列表
    public PageInfo<RiskContractModel> getPageRiskContract(RiskControlModel model){
        return businessService.getRiskContract(model);
    }

    //分页获取危险股票列表
    public PageInfo<DangerousStockModel> getPageDangerousStock(String keyWord,Integer index,Integer size,Integer orderBy) throws RiskException{
        try {
            return businessService.getPageDangerousStock(keyWord,index,size,orderBy);
        } catch (BusinessException e) {
            throw new RiskException(e.getMessage());
        }
    }

    //推进动态黑名单
    public void updateBlackOrder(IdModel model) throws RiskException{
        try {
            itemService.updateBlackStock(model);
        } catch (TransactionSummaryException e) {
            throw new RiskException(e.getMessage());
        }
    }

    //获取高危合约清单
    public PageInfo<RiskContractModel> getDangerousContract(String keyWord,int index,int size,int orderBy,double warnRate1,double warnRate2) throws RiskException{
        try {
            return businessService.getDangerousContract(keyWord,index,size,orderBy,warnRate1,warnRate2);
        } catch (BusinessException e) {
            throw new RiskException(e.getMessage());
        }
    }
}
