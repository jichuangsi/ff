package cn.com.fintheircing.admin.useritem.service.impl;

import cn.com.fintheircing.admin.system.utils.MappingEntity2ModelConverter;
import cn.com.fintheircing.admin.useritem.common.Status;
import cn.com.fintheircing.admin.useritem.dao.mapper.TransactionSummaryMapper;
import cn.com.fintheircing.admin.useritem.dao.repository.TransactionSummaryRepository;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import cn.com.fintheircing.admin.useritem.utils.MappingModel2EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
/**
 * Demo class
 *
 * @author yaoxiong
 * @date 2018/12/27
 */
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TransactionSummaryMapper transactionSummaryMapper;
    @Autowired
    private TransactionSummaryRepository transactionSummaryRepository;
    @Override
    public List<TransactionModel> findAllByWhite(TransactionModel model) {
        List<TransactionModel> list = findAllList(model);
        list.forEach(i->{
            if(!i.getStatus().getName().equals(Status.ABSOLUTE_WHITELIST)){
                list.remove(i);
            }
        });
        return list;
    }
    @Override
    public List<TransactionModel> findAllWhiteList(TransactionModel model) {
        List<TransactionModel> list = findAllList(model);
        list.forEach(i->{
            if(!i.getStatus().getName().equals(Status.WHITELIST)){
                list.remove(i);
            }
        });
        return list;
    }

    @Override
    public List<TransactionModel> findAllBlackList(TransactionModel model) {
        List<TransactionModel> list = findAllList(model);
        list.forEach(i->{
            if(!i.getStatus().getName().equals(Status.DYNAMIC_BLACKLIST)){
                list.remove(i);
            }
        });
        return list;
    }

    @Override
    public List<TransactionModel> findAllByBlack(TransactionModel model) {
        List<TransactionModel> list = findAllList(model);
        list.forEach(i->{
            if(!i.getStatus().getName().equals(Status.STATIC_BLACKLIST)){
                list.remove(i);
            }
        });
        return list;
    }
    @Override
    public List<TransactionModel> findAll(TransactionModel model) {
        List<TransactionModel> list = transactionSummaryMapper.findAllByTemplateAndStockName(model);
        return list;
    }

    @Override
    public int updateRemark(TransactionModel model) {
        return transactionSummaryMapper.updateRemark(model);
    }

    @Override
    public TransactionModel saveTransactionSummary(TransactionModel model) {
        return MappingEntity2ModelConverter.coverWithAbsoluteWhiteList(
                transactionSummaryRepository.save(
                        MappingModel2EntityConverter.coverWithAbsoluteWhiteList(model)));

    }

    @Override
    public int deleteTransactionSummary(String[] ids) {
        int a=0;
        for (String id:ids
             ) {
            a+=transactionSummaryMapper.updateTransactionSummary(id);
        }
        return a;
    }

    @Override
    public TransactionModel saveByStaticList(TransactionModel model) {
        return MappingEntity2ModelConverter.coverWithStaticBlackList(
                transactionSummaryRepository.save(
                        MappingModel2EntityConverter.coverWithStaticBlackList(model)));

    }
    public final List<TransactionModel> findAllList(TransactionModel model){
        List<TransactionModel> list = transactionSummaryMapper.findAllByTemplateAndStockName(model);
        return list;
    }
}
