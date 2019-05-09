package cn.com.fintheircing.admin.useritem.service.impl;

import cn.com.fintheircing.admin.business.utils.BusinessUtils;
import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.common.feign.model.QuotesTenModel;
import cn.com.fintheircing.admin.common.model.IdModel;
import cn.com.fintheircing.admin.system.utils.MappingEntity2ModelConverter;
import cn.com.fintheircing.admin.useritem.common.TransactionSummaryStatus;
import cn.com.fintheircing.admin.useritem.dao.mapper.TransactionSummaryMapper;
import cn.com.fintheircing.admin.useritem.dao.repository.TransactionSummaryRepository;
import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import cn.com.fintheircing.admin.useritem.exception.ImportException;
import cn.com.fintheircing.admin.useritem.exception.TransactionSummaryException;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;
import cn.com.fintheircing.admin.useritem.service.ItemService;
import cn.com.fintheircing.admin.useritem.utils.MappingModel2EntityConverter;
import cn.com.fintheircing.admin.useritem.utils.ReadExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
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
     *
     * @param model
     * @return
     */
    @Override
    public List<TransactionModel> findAllByWhite(TransactionModel model) {
        List<TransactionModel> allByWhite = transactionSummaryMapper.findAllByWhite(model);
        allByWhite.forEach(a -> {
            if (a.getStatus().equalsIgnoreCase("1")) {
                a.setStatus(TransactionSummaryStatus.getName(1));
            }

        });
        return allByWhite;
    }

    @Override
    public List<TransactionModel> findAllWhiteList(TransactionModel model) {
        List<TransactionModel> allByTemplateAndStockName = transactionSummaryMapper.findAllByTemplateAndStockName(model);
        allByTemplateAndStockName.forEach(a -> {
            if (a.getStatus().equalsIgnoreCase("0")) {
                a.setStatus(TransactionSummaryStatus.getName(0));
            }

        });
        return allByTemplateAndStockName;
    }

    @Override
    public List<TransactionModel> findAllBlackList(TransactionModel model) {
        List<TransactionModel> allBlackList = transactionSummaryMapper.findAllBlackList(model);
        allBlackList.forEach(a -> {
            if (a.getStatus().equalsIgnoreCase("3")) {
                a.setStatus(TransactionSummaryStatus.getName(3));
            }

        });
        return null;
    }

    /**
     * 静态黑名单
     *
     * @param model
     * @return
     */
    @Override
    public List<TransactionModel> findAllByBlack(TransactionModel model) {
        List<TransactionModel> allByBlack = transactionSummaryMapper.findAllByBlack(model);
        allByBlack.forEach(a -> {
            if (a.getStatus().equalsIgnoreCase("2")) {
                a.setStatus(TransactionSummaryStatus.getName(2));
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
    public int updateRemark(String stockNum, String mark) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", stockNum);
        map.put("mark", mark);
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
    public Boolean isExistWhiteList(String stockNum) {
        Boolean flag = false;
        List<TransactionModel> list = findAllWhiteList(new TransactionModel());
        list.forEach(i -> {
            if (!i.getStatus().equals(TransactionSummaryStatus.WHITELIST.getName())) {
                list.remove(i);
            }
        });
        for (TransactionModel transactionModel : list) {
            if (transactionModel.getStockNum().equals(stockNum)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 导入上传excel文件
     *
     * @param
     * @param files
     * @return
     */
    @Override
    public boolean importExcel(MultipartFile[] files) throws ImportException {
        Map<Integer, Map<Integer, Object>> map = new HashMap<>();
        try {
            for (MultipartFile file : files) {
                map = ReadExcelUtil.readExcelContentz(file);
                if (map.size() < 0) {
                    return false;
                } else {
                    //excel数据存在map里，map.get(0).get(0)为excel第1行第1列的值，此处可对数据进行处理
                    for (int i = 0; i < map.size(); i++) {
                        TransactionSummary t = new TransactionSummary();
                        for (int j = 0; j < map.get(i).size(); j++) {

                            switch (j) {
                                case 0: {
                                    if (StringUtils.isEmpty(map.get(i).get(j))) {
                                        t.setStockNum("");
                                        break;
                                    } else {
                                        t.setStockNum(map.get(i).get(j).toString());
                                        break;
                                    }
                                }
                                case 1: {
                                    if (StringUtils.isEmpty(map.get(i).get(j))) {
                                        t.setStockName("");
                                        break;
                                    } else {
                                        t.setStockName(map.get(i).get(j).toString());

                                        break;
                                    }
                                }
                                case 2: {
                                    if (StringUtils.isEmpty(map.get(i).get(j))) {
                                        t.setAlphabetCapitalization("");
                                        break;
                                    } else {
                                        t.setAlphabetCapitalization(map.get(i).get(j).toString());

                                        break;
                                    }
                                }
                                case 3: {
                                    if (StringUtils.isEmpty(map.get(i).get(j))) {
                                        t.setMartTemplate("");
                                        break;
                                    } else {
                                        t.setMartTemplate(map.get(i).get(j).toString());

                                        break;
                                    }
                                }
                                case 4: {
                                    if (StringUtils.isEmpty(map.get(i).get(j))) {
                                        t.setJoinTime(new Date());
                                        break;
                                    } else {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date parse = sdf.parse(map.get(i).get(j).toString());
                                        t.setJoinTime(parse);

                                        break;
                                    }
                                }
                                case 5: {
                                    if (StringUtils.isEmpty(map.get(i).get(j))) {
                                        t.setRemake("");
                                        break;
                                    } else {
                                        t.setRemake(map.get(i).get(j).toString());

                                        break;
                                    }
                                }
                                default:
                                    throw new ImportException("无数据");
                            }
                        }
                        if (transactionSummaryRepository.findOneByStockNum(t.getStockNum()) != null) {
                            continue;
                        } else {
                            TransactionSummary save = transactionSummaryRepository.save(t);
                        }
                    }
                }
            }
            return true;

        } catch (Exception e) {
            throw new ImportException(e.getMessage());
        }

    }

    @Override
    public void oneDayUpdateStock(List<QuotesTenModel> quotesTenModels) {
        List<TransactionSummary> summaries = transactionSummaryRepository.findByDeleteFlag("0");
        for (QuotesTenModel model : quotesTenModels) {
            for (TransactionSummary summary : summaries) {
                if (model.getStockCode().equals(summary.getStockNum())) {
                    if (null == summary.getNowCursor()) {
                        summary.setNowCursor(0);
                    }
                    int cursor = summary.getNowCursor() == 5 ? 1 : BusinessUtils.addIntMethod(summary.getNowCursor(), 1);
                    summary.setNowCursor(cursor);
                    switch (cursor) {
                        case 1:
                            summary.setOneDay((double) model.getAmount());
                            break;
                        case 2:
                            summary.setTwoDay((double) model.getAmount());
                            break;
                        case 3:
                            summary.setThreeDay((double) model.getAmount());
                            break;
                        case 4:
                            summary.setFourDay((double) model.getAmount());
                            break;
                        case 5:
                            summary.setFiveDay((double) model.getAmount());
                            break;
                    }
                    break;
                }
            }
        }
        transactionSummaryRepository.saveAll(summaries);
    }

    @Override
    public void updateBlackStock(IdModel model) throws TransactionSummaryException {
        List<String> ids = model.getIds();
        List<TransactionSummary> summaries = transactionSummaryRepository.findAllByIdIn(ids);
        if (0 == summaries.size()) {
            throw new TransactionSummaryException(ResultCode.SELECT_NULL_MSG);
        }
        for (TransactionSummary summary : summaries) {
            summary.setStatus(TransactionSummaryStatus.DYNAMIC_BLACKLIST.getIndex());
        }
        transactionSummaryRepository.saveAll(summaries);
    }

    @Override
    public String getStockIdByStockNameAndStockCode(String stockName, String stockCode) {
        TransactionSummary summary = transactionSummaryRepository.findByStockNameContainingAndStockNum(stockName, stockCode);
        if (null != summary) {
            return summary.getId();
        }
        return null;
    }

    @Override
    public Map<String, String> getStockNameAndStockCodeById(String id) {
        TransactionSummary summary = transactionSummaryRepository.findOneById(id);
        Map<String, String> map = new HashMap<String, String>();
        if (null != summary) {
            map.put("name", summary.getStockName());
            map.put("code", summary.getStockNum());
        }
        return map;
    }

    @Override
    public String getStockCodeByStockId(String stockId) {
        TransactionSummary summary = transactionSummaryRepository.findOneById(stockId);
        if (null != summary) {
            return summary.getStockNum();
        }
        return "";
    }
}