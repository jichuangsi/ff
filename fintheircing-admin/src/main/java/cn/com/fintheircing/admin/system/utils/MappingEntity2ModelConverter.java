package cn.com.fintheircing.admin.system.utils;

import cn.com.fintheircing.admin.system.entity.SystemAgreement;
import cn.com.fintheircing.admin.system.entity.SystemBankCard;
import cn.com.fintheircing.admin.system.entity.SystemCompany;
import cn.com.fintheircing.admin.system.entity.SystemPhoto;
import cn.com.fintheircing.admin.system.model.agreement.AgreementModel;
import cn.com.fintheircing.admin.system.model.bank.BankCardModel;
import cn.com.fintheircing.admin.system.model.company.CompanyModel;
import cn.com.fintheircing.admin.system.model.photo.PhotoModel;
import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import cn.com.fintheircing.admin.useritem.model.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class MappingEntity2ModelConverter {
    public static final List<TransactionModel> coverListSummaryWithAbsoluteWhiteList(List<TransactionSummary> Summary) {
        List<TransactionModel> models = new ArrayList<>();
        TransactionModel model = new TransactionModel();
        Summary.forEach(s -> {
            model.setAlphabetCapitalization(s.getAlphabetCapitalization());
            model.setJoinTime(s.getJoinTime());
            model.setMartTemplate(s.getMartTemplate());
            model.setRemake(s.getRemake());
            model.setId(s.getId());
            model.setStockNum(s.getStockNum());
            model.setStockName(s.getStockName());
            models.add(model);
        });
        return models;
    }

    public static final TransactionModel coverWithAbsoluteWhiteList(TransactionSummary Summary) {
        TransactionModel model = new TransactionModel();
        model.setAlphabetCapitalization(Summary.getAlphabetCapitalization());
        model.setJoinTime(Summary.getJoinTime());
        model.setMartTemplate(Summary.getMartTemplate());
        model.setRemake(Summary.getRemake());
        model.setStockNum(Summary.getStockNum());
        model.setId(Summary.getId());
        model.setStockName(Summary.getStockName());
//        model.setStatus(Summary.getStatus().getName());
        return model;
    }

    public static final TransactionModel coverWithStaticBlackList(TransactionSummary summary) {
        TransactionModel model = new TransactionModel();
        model.setAlphabetCapitalization(summary.getAlphabetCapitalization());
        model.setJoinTime(summary.getJoinTime());
        model.setMartTemplate(summary.getMartTemplate());
        model.setRemake(summary.getRemake());
        model.setId(summary.getId());
        model.setStockNum(summary.getStockNum());
        model.setStockName(summary.getStockName());
//        model.setStatus(summary.getStatus().getName());
        return model;
    }

    public static final PhotoModel CONVERTERFROMSYSTEMPHOTO(SystemPhoto photo){
        PhotoModel model = new PhotoModel();
        model.setApply(photo.getApply());
        model.setId(photo.getUuid());
        model.setModifyTime(photo.getUpdatedTime());
        model.setName(photo.getName());
        model.setOn(photo.getStayOn());
        return model;
    }

    public static final BankCardModel CONVERTERFROMSYSTEMBANKCARD(SystemBankCard card){
        BankCardModel model = new BankCardModel();
        model.setBankCardNo(card.getBankCardNo());
        model.setBankName(card.getBankName());
        model.setId(card.getUuid());
        model.setName(card.getName());
        model.setStatus(card.getStatus());
        model.setUpdateTime(card.getUpdatedTime());
        return model;
    }

    public static CompanyModel CONVERTERFROMSYSTEMCOMPANY(SystemCompany company){
        CompanyModel model = new CompanyModel();
        model.setApply(company.getApply());
        model.setCompanyName(company.getCompanyName());
        model.setCopyright(company.getCopyright());
        model.setEmail(company.getEmail());
        model.setHotLine(company.getHotLine());
        model.setIcpNo(company.getIcpNo());
        model.setId(company.getUuid());
        model.setModifyTime(company.getUpdatedTime());
        model.setName(company.getName());
        model.setQqAccount(company.getQqAccount());
        model.setServerTime(company.getServerTime());
        model.setWechatAccount(company.getWechatAccount());
        return model;
    }

    public static AgreementModel CONVERTERFROMSYSTEMAGREEMENT(SystemAgreement agreement){
        AgreementModel model  = new AgreementModel();
        model.setApply(agreement.getApply());
        model.setApplyOn(agreement.getApplyOn());
        model.setContent(agreement.getContent());
        model.setId(agreement.getUuid());
        model.setName(agreement.getName());
        model.setUpdateTime(agreement.getUpdatedTime());
        return model;
    }
}
