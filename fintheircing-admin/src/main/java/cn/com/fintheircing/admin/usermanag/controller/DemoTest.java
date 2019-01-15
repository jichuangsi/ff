package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.proxy.dao.repository.IAdminClientInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoTest {
    @Resource
   private IAdminClientInfoRepository iAdminClientInfoRepository;
    @Test
    public void test(){
        for (int i=0;i<10;i++) {
            AdminClientInfo a = new AdminClientInfo();
            a.setBossId("111");
            a.setName("黄晓敏");
            a.setPhone("123");
            a.setProxyNum("456");
            a.setRemarks("测试");
            a.setRoleGrade(0);
            a.setStatus("0");
            a.setUserName("aaa");
            System.out.println(a.toString());
           AdminClientInfo save = iAdminClientInfoRepository.save(a);
            System.out.println(123);

        }
    }
}
