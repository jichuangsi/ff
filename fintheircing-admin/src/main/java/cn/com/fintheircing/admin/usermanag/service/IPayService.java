package cn.com.fintheircing.admin.usermanag.service;

import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.model.pay.AppQueryModel;
import cn.com.fintheircing.admin.usermanag.model.pay.NetQueryModel;
import cn.com.fintheircing.admin.usermanag.model.pay.ResultModel;
import cn.com.fintheircing.admin.usermanag.model.result.*;

public interface IPayService {
    /**
     * 获取网关支付信息并且返回支付地址
     * @param model
     * @return
     * @throws UserServiceException
     */
   ResultModel getWayToPay(NetQueryModel model) throws UserServiceException;

    /**
     * 第支付宝微信支付信息并且返回支付
     * @param model
     * @return
     * @throws UserServiceException
     */
    ResultModel getWechatOrAilpayInfo(AppQueryModel model)throws UserServiceException;

    /**
     * 网关二维码支付
     * @param model
     * @return
     * @throws UserServiceException
     */
//    ResultModel gatewayPayByQRcode(NetQueryModel model)throws UserServiceException;

    /**
     * 支付结果查询
     * @param model
     * @return
     * @throws UserServiceException
     */
    BillResponseModel  queryPayResult(BillQueryModel model)throws UserServiceException;

    /**
     * 支付返回所有结果集
     * @param modelList
     * @return
     * @throws UserServiceException
     */
    boolean queryReconTrans(BillQueryModel modelList, String orderId)throws UserServiceException;


}
