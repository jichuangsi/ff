package cn.com.fintheircing.admin.test;

import cn.com.fintheircing.admin.business.dao.mapper.IBusinessContractMapper;
import cn.com.fintheircing.admin.business.dao.mapper.IBusinessStockHoldingMapper;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessContractRiskRepository;
import cn.com.fintheircing.admin.business.dao.repository.IBusinessStockHoldingRepository;
import cn.com.fintheircing.admin.business.exception.BusinessException;
import cn.com.fintheircing.admin.business.model.ContractModel;
import cn.com.fintheircing.admin.business.model.StockEntrustModel;
import cn.com.fintheircing.admin.business.model.StockHoldingModel;
import cn.com.fintheircing.admin.business.model.StockModel;
import cn.com.fintheircing.admin.business.service.BusinessService;
import cn.com.fintheircing.admin.business.utils.BusinessUtils;
import cn.com.fintheircing.admin.useritem.dao.repository.TransactionSummaryRepository;
import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRollBack
{
    @Resource
    private BusinessService service;
    @Resource
    private IBusinessContractRiskRepository businessContractRiskRepository;
    @Resource
    private IBusinessStockHoldingRepository businessStockHoldingRepository;
    @Resource
    private IBusinessContractMapper businessContractMapper;
    @Resource
    private TransactionSummaryRepository transactionSummaryRepository;
    @Resource
    private IBusinessStockHoldingMapper businessStockHoldingMapper;

    @Test
    public void main(){
        try {
            service.testRollBack();
        } catch (BusinessException e) {
            System.out.println("111");
        } finally {
        }
    }

    @Test
    public void testRisk() throws BusinessException{
        StockModel stockModel = new StockModel();//根据stockNum获取当前股票实时数据
        //BusinessContractRisk risk = businessContractRiskRepository.findBusinessContractRiskByContractId("40289f1a686084460168608694110000");
        StockHoldingModel stockHoldingModel = businessStockHoldingMapper.selectStockNum("40289f1a686084460168608694110000","600600");
        ContractModel contract = businessContractMapper.selectContract("40289f1a686084460168608694110000");//获取相关合约
        stockModel.setYesterdayClose(54.0);
        stockModel.setTodayMax(55.0);
        stockModel.setTodayMin(48.0);
        stockModel.setTodayOpen(54.32);
        StockEntrustModel model = new StockEntrustModel();
        model.setAmount(300);
        model.setPrice(53.0);
        model.setStockNum("600600");
        BusinessUtils.throughRisk(stockHoldingModel,model,contract,stockModel);

    }

    @Test
    public void testSaveStock(){
        TransactionSummary summary = new TransactionSummary();
        summary.setStockNum("600678");
        summary.setJoinTime(new Date());
        summary.setRemake("测试");
        summary.setStockName("测试");
        transactionSummaryRepository.save(summary);
    }
}
