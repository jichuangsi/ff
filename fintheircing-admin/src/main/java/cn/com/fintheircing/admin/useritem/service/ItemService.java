package cn.com.fintheircing.admin.useritem.service;

import cn.com.fintheircing.admin.common.feign.model.QuotesTenModel;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.useritem.exception.ImportException;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * interface
 *
 * @author 姚雄
 * @date 2016/10/31
 */
@Transactional
public interface ItemService {
    List<TransactionModel> findAllByWhite(TransactionModel model);
    List<TransactionModel> findAllByBlack(TransactionModel model);

    int updateRemark(String id,String mark);

    TransactionModel saveTransactionSummary(TransactionModel model);

    int deleteTransactionSummary(IdModel ids);

    List<TransactionModel> findAllBlackList(TransactionModel model);
    List<TransactionModel> findAllWhiteList(TransactionModel model);
    TransactionModel saveByStaticList(TransactionModel model);
    List<TransactionModel> findAll(TransactionModel model);

     //匹配是否属于白名单
    Boolean isExistWhiteList(String stockNum);

    /**
     * 导入上传excel文件
     * @param
     * @param file
     * @return
     */
    boolean importExcel(MultipartFile[] file) throws ImportException;

    void oneDayUpdateStock(List<QuotesTenModel> quotesTenModels);
}
