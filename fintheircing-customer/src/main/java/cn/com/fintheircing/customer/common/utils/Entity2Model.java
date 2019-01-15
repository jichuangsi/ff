package cn.com.fintheircing.customer.common.utils;

import org.springframework.beans.factory.annotation.Value;

public class Entity2Model {
    @Value("${custom.token.longTime}")
    private Long longTime;

//    public final static List<OnlineUserInfo>  coverUserClientLoginInfo(List<UserClientLoginInfo> users){
//        List<OnlineUserInfo> models = new ArrayList<OnlineUserInfo>();
//        OnlineUserInfo model=new OnlineUserInfo();
//            users.forEach(user->{
//                model.setUserId(user.getUuid());
////                model.setExpiredTime(user.);
//                model.setIpAdress(user.getIpAddress());
//                model.setLastTime(user.getLastTime());
//                model.setLoginTime(user.getLoginTime());
//                model.setUserName(user.getLoginName());
//                model.setStatus(user.getStatus());
//                models.add(model);
//            });
//                return models;
//    }

}
