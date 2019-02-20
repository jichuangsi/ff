package cn.com.fintheircing.customer.test;

import cn.com.fintheircing.customer.business.exception.BusinessException;
import cn.com.fintheircing.customer.business.model.ContractModel;
import cn.com.fintheircing.customer.business.model.ProductModel;
import cn.com.fintheircing.customer.business.model.tranfer.TranferProductModel;
import cn.com.fintheircing.customer.business.service.BusinessService;
import cn.com.fintheircing.customer.common.feign.IAdminFeignService;
import cn.com.fintheircing.customer.user.model.UserTokenInfo;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDemo {

    private String userToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTW9kZWwiOiJ7XCJkaXNwbGF5bmFtZVwiOlwiMTM5MjI3Nzg5NTNcIixcInBob25lXCI6XCIxMzkyMjc3ODk1M1wiLFwicm9sZUdyYWRlXCI6NixcInJvbGVOYW1lXCI6XCLnlKjmiLdcIixcInN0YXR1c1wiOlwiMFwiLFwidXNlck5hbWVcIjpcIjEzOTIyNzc4OTUzXCIsXCJ1dWlkXCI6XCI0MDI4OWYxYTY4MjY2YTRhMDE2ODI2NzFmMWM3MDAwMFwifSIsImV4cCI6MTU0NjgzMzY3OX0.N8r05amHIG_5AK8B8vs5rxMaJLG_qOJkycXTPbZgcvHyo00gtx9givLiHl-A_WmABHO7rGWGlMBcjcWe0diFiw";

    @Value("${custom.token.claim}")
    private String userClaim;
    @Resource
    private BusinessService businessService;

    @Test
    public void saveContractTest() {
        DecodedJWT jwt = JWT.decode(userToken);
        String user = jwt.getClaim(userClaim).asString();
        UserTokenInfo userInfo = JSONObject.parseObject(user, UserTokenInfo.class);

        ProductModel productModel = new ProductModel();//产品
        productModel.setAllot(0);
        TranferProductModel tranferProductModel = new TranferProductModel();
        try {
            tranferProductModel = businessService.getTranferProductModel(userInfo,productModel);
        } catch (BusinessException e) {
            System.out.println("返回异常信息");
        }//获取产品列表和是否能购买，不能购买，购买按钮为灰
        productModel = tranferProductModel.getProductModels().get(0);//选择一种产品


        ContractModel contractModel = new ContractModel();//合约
        contractModel.setPromisedMoney(2000D);//保证金为2000
        contractModel.setProductModel(productModel);  //将产品信息放入合约内
        Boolean flag = businessService.isRich(userInfo,contractModel);  //判断账户余额是否充足

        if (flag){  //账户充足才可继续保存合约
            contractModel.setBorrowMoney(20000D);//需要前端计算借款
           /* try {
                businessService.saveContract(userInfo,contractModel);
            } catch (BusinessException e) {
                System.out.println("返回错误信息");
            }*/
        }

    }


    @Resource
    private IAdminFeignService adminFeignService;

   /* @Test
    public void testFeign(){
        adminFeignService.testFeign();
    }*/

   @Test
   public void testNull(){
       Map<String,String> map = new HashMap<String,String>();
       map.put("123",null);
       String string = map.get("123");
       System.out.println(string);
   }

}
