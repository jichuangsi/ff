package cn.com.fintheircing.admin.usermanag.service;

import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.model.pay.*;
import cn.com.fintheircing.admin.usermanag.model.promise.CheckPromiseModel;
import cn.com.fintheircing.admin.usermanag.model.promise.PromiseModel;
import cn.com.fintheircing.admin.usermanag.model.result.AppResultModel;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.model.result.*;

import java.util.List;

public interface IPayService {
    /**
     * 获取网关支付信息并且返回支付地址
     * @param model
     * @return
     * @throws UserServiceException
     */
   ResultModel getWayToPay(NetQueryModel model, PayConfigModel payConfig) throws UserServiceException;

    /**
     * 第支付宝微信支付信息并且返回支付
     * @param model
     * @return
     * @throws UserServiceException
     */
    ResultModel getWechatOrAilpayInfo(AppQueryModel model)throws UserServiceException;


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

    /**
     * 展示支付二维码
     * @param model
     * @param payConfig
     * @return
     * @throws UserServiceException
     */
    AppResultModel payForQRCode(AppQueryModel model, PayConfigModel payConfig) throws UserServiceException;

    /**
     * 返回申请
     * @param data
     * @return
     */
    CheckPromiseModel addPromiseMoney(PromiseModel data, UserTokenInfo userInfo);

     List<RecodeInfoPayModel> findAllPayInfo();

    int updatePayInfo(RecodeInfoPayModel model);
 /**
  * 查询所有申请追加保证金的个人的信息
  * @return
  */
     List<PromiseModel> findAllApply();

 /**
  * 同意追加保证金申请
  * @param userInfo
  * @param model
  * @return
  */
 boolean agreePromiseMoney(UserTokenInfo userInfo,PromiseModel model);

 /**
  * 驳回追加保证金申请
  * @param userInfo
  * @param model
  * @return
  */
   boolean passPromiseMoney(UserTokenInfo userInfo, PromiseModel model);
}
