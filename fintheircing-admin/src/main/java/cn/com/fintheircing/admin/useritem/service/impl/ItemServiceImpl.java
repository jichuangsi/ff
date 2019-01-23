package cn.com.fintheircing.admin.useritem.service.impl;

import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.system.utils.MappingEntity2ModelConverter;
import cn.com.fintheircing.admin.useritem.common.Status;
import cn.com.fintheircing.admin.useritem.dao.mapper.TransactionSummaryMapper;
import cn.com.fintheircing.admin.useritem.dao.repository.TransactionSummaryRepository;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import cn.com.fintheircing.admin.useritem.utils.MappingModel2EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author yaoxiong
 * @date 2018/12/27
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    private TransactionSummaryMapper transactionSummaryMapper;
    @Autowired
    private TransactionSummaryRepository transactionSummaryRepository;

    /**
     * 查找全部绝对白名单
     * @param model
     * @return
     */
    @Override
    public List<TransactionModel> findAllByWhite(TransactionModel model) {
        List<TransactionModel> allByWhite = transactionSummaryMapper.findAllByWhite(model);
        allByWhite.forEach(a->{
            if (a.getStatus().equalsIgnoreCase("1")){
                a.setStatus(Status.getName(1));
            }

        });
        return allByWhite;
    }

    @Override
    public List<TransactionModel> findAllWhiteList(TransactionModel model) {
        List<TransactionModel> allByTemplateAndStockName = transactionSummaryMapper.findAllByTemplateAndStockName(model);
        allByTemplateAndStockName.forEach(a->{
            if (a.getStatus().equalsIgnoreCase("0")){
                a.setStatus(Status.getName(0));
            }

        });
        return allByTemplateAndStockName;
    }

    @Override
    public List<TransactionModel> findAllBlackList(TransactionModel model) {
        List<TransactionModel> allBlackList = transactionSummaryMapper.findAllBlackList(model);
        allBlackList.forEach(a->{
            if (a.getStatus().equalsIgnoreCase("3")){
                a.setStatus(Status.getName(3));
            }

        });
        return null;
    }

    /**
     * 静态黑名单
     * @param model
     * @return
     */
    @Override
    public List<TransactionModel> findAllByBlack(TransactionModel model) {
        List<TransactionModel> allByBlack = transactionSummaryMapper.findAllByBlack(model);
        allByBlack.forEach(a->{
            if (a.getStatus().equalsIgnoreCase("2")){
                a.setStatus(Status.getName(2));
            }

        });
        return allByBlack;

    }

    @Override
    public List<TransactionModel> findAll(TransactionModel model) {
        List<TransactionModel> list = transactionSummaryMapper.findAll(model);
        return list;
    }



    @Override
    public int updateRemark(String stockNum,String mark) {
        Map<String,Object> map=new HashMap<>();
        map.put("id", stockNum);
        map.put("mark",mark);
        return transactionSummaryMapper.updateRemark(map);
    }

    @Override
    public TransactionModel saveTransactionSummary(TransactionModel model) {
        return MappingEntity2ModelConverter.coverWithAbsoluteWhiteList(
                transactionSummaryRepository.save(
                        MappingModel2EntityConverter.coverWithAbsoluteWhiteList(model)));
    }

    @Override
    public int deleteTransactionSummary(IdModel ids) {
        List<String> ids1 = ids.getIds();
        int a = 0;
        for (String id : ids1
        ) {
            a += transactionSummaryMapper.updateTransactionSummary(id);
        }
        return a;
    }

    @Override
    public TransactionModel saveByStaticList(TransactionModel model) {
        return MappingEntity2ModelConverter.coverWithStaticBlackList(
                transactionSummaryRepository.save(
                        MappingModel2EntityConverter.coverWithStaticBlackList(model)));

    }


    @Override
    public Boolean isExistWhiteList(String  stockNum) {
        Boolean flag = false;
        List<TransactionModel> list = findAllWhiteList(new TransactionModel());
        list.forEach(i->{
            if(!i.getStatus().equals(Status.WHITELIST.getName())){
                list.remove(i);
            }
        });
        for (TransactionModel transactionModel:list){
            if (transactionModel.getStockNum().equals(stockNum)){
                flag = true;
            }
        }
        return flag;
    }
}

