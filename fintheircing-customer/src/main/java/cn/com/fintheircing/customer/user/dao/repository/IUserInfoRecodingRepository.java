package cn.com.fintheircing.customer.user.dao.repository;

import cn.com.fintheircing.customer.user.entity.UserInfoRecording;


public interface IUserInfoRecodingRepository /*extends JpaRepository<UserInfoRecording,String> */{
    UserInfoRecording findOneById(String id);

}
