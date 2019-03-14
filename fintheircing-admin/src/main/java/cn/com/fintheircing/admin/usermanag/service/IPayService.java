package cn.com.fintheircing.admin.usermanag.service;

import cn.com.fintheircing.admin.common.model.UserTokenInfo;
import cn.com.fintheircing.admin.usermanag.model.pay.*;
import cn.com.fintheircing.admin.usermanag.model.result.AppResultModel;
import cn.com.fintheircing.admin.usermanag.Excption.UserServiceException;
import cn.com.fintheircing.admin.usermanag.model.result.*;

import java.util.List;

public interface IPayService {

    /**
     * 第支付宝微信支付信息并且返回支付
     * @param model
     * @return
     * @throws UserServiceException
     */
    ResultModel getWechatOrAilpayInfo(PayInfoModel allPayInfo1,AppQueryModel model)throws UserServiceException;


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
     * 查询所有未审核的
     * @return
     * @throws UserServiceException
     */
     List<RecodeInfoPayModel> findAllPayInfo() throws UserServiceException;

    int updatePayInfo(RecodeInfoPayModel model);

 /**
  * 同意追加保证金申请
  * @param userInfo
  * @param model
  * @return
  */
 boolean agreePromiseMoney(UserTokenInfo userInfo,RecodeInfoPayModel model) throws UserServiceException;

 /**
  * 驳回追加保证金申请
  * @param userInfo
  * @param model
  * @return
  */
   boolean passPromiseMoney(UserTokenInfo userInfo, RecodeInfoPayModel model);

 /**
  * 提现申请
  * @param userInfo
  * @param model
  * @return
  */
    boolean agreewithdrawCash(UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException;

 /**
  * 驳回提现申请
  * @param userInfo
  * @param model
  * @return
  */
 boolean passwithdrawCash(UserTokenInfo userInfo, RecodeInfoPayModel model);

 boolean expendMoney(UserTokenInfo userInfo, RecodeInfoPayModel model) throws UserServiceException;
}
