package cn.com.fintheircing.customer.user.dao.repository;

import cn.com.fintheircing.customer.user.entity.UserInfoRecording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserInfoRecodingRepository extends JpaRepository<UserInfoRecording,String> {
    UserInfoRecording findOneById(String id);

}
