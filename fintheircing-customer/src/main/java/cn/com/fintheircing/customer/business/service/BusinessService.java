package cn.com.fintheircing.customer.business.service;

import cn.com.fintheircing.customer.business.exception.BusinessException;
import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.business.model.StockHoldingModel;
import cn.com.fintheircing.customer.business.model.tranfer.TranferProductModel;
import cn.com.fintheircing.customer.common.constant.ProductStatus;
import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.common.feign.model.StockEntrustModel;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.dao.repository.IUserAccountRepository;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import cn.com.fintheircing.customer.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusinessService {
    @Resource
    private IUserAccountRepository userAccountRepository;
    @Resource
    private UserService userService;
    @Resource
    private IAdminFeignService adminFeignService;

    @Value("${custom.contract.dateFormat}")
    private String sdformat;

    public TranferProductModel getTranferProductModel(UserTokenInfo userInfo,ProductModel model) throws BusinessException{
        List<ProductModel> productModels = adminFeignService.getProductModels(model.getAllot());
        if (!(productModels.size()>0)){
            throw new BusinessException( ResultCode.PRODUCT_GET_ERR);
        }
        productModels = getProductStatusName(productModels);
        TranferProductModel tranferProductModel = new TranferProductModel();
        tranferProductModel.setProductModels(productModels);
        if (adminFeignService.canBuy(model.getAllot(),userInfo.getUuid())){
            tranferProductModel.setCanBuy(ResultCode.SUCESS_INFO);
        }else{
            tranferProductModel.setCanBuy(ResultCode.ERR_INFO);
        }
        return tranferProductModel;
    }

    public Boolean isRich(UserTokenInfo userInfo, ContractModel model){
        Double account = userAccountRepository.findAccountByUserId(userInfo.getUuid());
        ProductModel productModel = adminFeignService.getProduct(model.getProductId());
        model.setProductModel(productModel);
        if (account!=null){
            if (account>getNeedMoney(model.getProductModel(),model.getPromisedMoney())+model.getPromisedMoney()){
                return true;
            }
        }
        return false;
    }


    //判断资金是否充足并返回产品
    private ProductModel getProductIsRich(UserTokenInfo userInfo, ContractModel model) throws BusinessException{
        Double account = userAccountRepository.findAccountByUserId(userInfo.getUuid());
        ProductModel productModel = adminFeignService.getProduct(model.getProductId());
        if (productModel==null){
            throw new BusinessException(ResultCode.PRODUCT_NOTEXIST_ERR);
        }
        model.setProductModel(productModel);
        if (account!=null){
            if (account>getNeedMoney(model.getProductModel(),model.getPromisedMoney())+model.getPromisedMoney()){
                return productModel;
            }
        }
        return null;
    }

    //保存合约，扣除用户账户，创建合约操作
    @Transactional(rollbackFor = Exception.class)
    public void saveContract(UserTokenInfo userInfo,ContractModel model) throws BusinessException{
        if (!adminFeignService.canBuy(model.getProductModel().getAllot(),userInfo.getUuid())){
            throw new BusinessException(ResultCode.PRODUCT_ISEXIST_ERR);
        }
        ProductModel productModel = getProductIsRich(userInfo,model);
        if (productModel == null){
            throw new BusinessException(ResultCode.ACCOUNT_LESS_ERR);
        }//再次查看余额是否足够购买，并锁住表（悲观锁）
        model.setProductModel(productModel);
        //保证数据安全，重新获取产品信息
        model.setFirst(getNeedMoney(model.getProductModel(),model.getPromisedMoney()));
        if (!(userAccountRepository.updatePayAccount(model.getPromisedMoney()+model.getFirst(),userInfo.getUuid())>0)){
            throw new BusinessException(ResultCode.ACCOUNT_PAY_ERR);
        }//扣款先
        model.setBorrowMoney(model.getPromisedMoney()*model.getProductModel().getLeverRate());
        model.setContractNum(newContractNum());
        model.setChoseWay(model.getProductModel().getAllot());
        model.setWarningLine(model.getProductModel().getWornLine());
        model.setAbortLine(model.getProductModel().getLiquidation());
        model.setUserId(userInfo.getUuid());
        model.setSaleManId(userService.getSaleManId(userInfo.getUuid()));

        ResponseModel<String> response = adminFeignService.saveContract(model);
        if (!ResultCode.SUCESS.equals(response.getCode())){
            throw new BusinessException(response.getMsg());
        }
    }


    //初次利息
    private Double getNeedMoney(ProductModel productModel,Double promised){
        Double sumMoney = promised*productModel.getLeverRate();
        return sumMoney*productModel.getFinancingTime()*productModel.getMoneyInContact();
    }

    //包装model
    private List<ProductModel> getProductStatusName(List<ProductModel> ps){
        List<ProductModel> productModels = new ArrayList<>();
        ps.forEach(p->{
            p.setAllotStr(ProductStatus.getName(p.getAllot()));
            productModels.add(p);
        });
        return productModels;
    }

    //合约编号
   /* private String newContractNum(int i) throws BusinessException{
        if (i>4){
            throw new BusinessException(ResultCode.CONTRACT_SAVE_ERR);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(sdformat);
        String contractNum = sdf.format(new Date())+(int)Math.random()*10000;
        if (!adminFeignService.existContractNum(contractNum)){
            newContractNum(i+1);
        }
        return contractNum;
    }*/
   private String newContractNum(){
       SimpleDateFormat sdf = new SimpleDateFormat(sdformat);
       return sdf.format(new Date())+(int)Math.random()*10000;
   }


   //获取当前用户当前的合约
   public List<ContractModel> getCurrentContract(UserTokenInfo userInfo){
       return adminFeignService.getCurrentContract(userInfo.getUuid());
   }


   public void saveEntrust(UserTokenInfo userInfo, StockEntrustModel model) throws BusinessException{
        /*if (!adminFeignService.isExistWhiteList(model.getStockNum())){
            throw new BusinessException(ResultCode.STOCK_DANGER_ERR);
        }*///验证是否存在白名单,暂时接口不可用
        //保存购买股票申请单，并冻结资金
        model.setUserId(userInfo.getUuid());
        ResponseModel<String> response = adminFeignService.saveStockEntrust(model);
        if (response==null){
            throw new BusinessException("响应失败");
        }
        if (!ResultCode.SUCESS.equals(response.getCode())){
            throw new BusinessException(response.getMsg());
        }
   }

   public StockHoldingModel getCurrentStockHolding(UserTokenInfo userInfo,
                                                   StockHoldingModel model) throws BusinessException{
       model.setUserId(userInfo.getUuid());
       model = adminFeignService.getCurrentHolding(model);
       if (model==null){
           throw new BusinessException(ResultCode.STOCK_HOLD_ERR);
       }
       return model;
   }

   public void sellHoldStockEntrust(UserTokenInfo userInfo,StockHoldingModel model) throws BusinessException{
       /*StockHoldingModel stockHoldingModel = getCurrentStockHolding(userInfo,model);
        if (model.getAmount()>stockHoldingModel.getCanSell()){
            throw new BusinessException(ResultCode.STOCK_SELL_LESS_ERR);
        }
        model.setMotherAccount(stockHoldingModel.getMotherAccount());
        model.setId(stockHoldingModel.getId());*/
       model.setUserId(userInfo.getUuid());
       ResponseModel<String> response = adminFeignService.sellHoldStockEntrust(model);
       if (!ResultCode.SUCESS.equals(response.getCode())){
           throw new BusinessException(response.getMsg());
       }
   }

    public List<StockEntrustModel> getUnfinishedEntrust(UserTokenInfo userInfo,ContractModel model){
        model.setUserId(userInfo.getUuid());
        return adminFeignService.getUnFinishedEntrust(model);
    }


    public void cancelOrder(UserTokenInfo userInfo,StockEntrustModel model) throws BusinessException{
       model.setUserId(userInfo.getUuid());
       ResponseModel responseModel = adminFeignService.entrustCancelOrder(model);
       if (!ResultCode.SUCESS.equals(responseModel.getCode())){
           throw new BusinessException(responseModel.getMsg());
       }
    }


    public StockHoldingModel getMaxBuyAmount(UserTokenInfo userInfo,StockHoldingModel model) throws BusinessException{
       /*if (model.getCostPrice()==null||0==model.getCostPrice()){
           throw new BusinessException("有不花钱买股票的嘛？没点b数");
       }
       model.setUserId(userInfo.getUuid());*/
       return adminFeignService.getMaxBuyAmount(model);
    }
}
