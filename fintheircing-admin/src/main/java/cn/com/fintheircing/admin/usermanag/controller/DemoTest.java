package cn.com.fintheircing.admin.usermanag.controller;

import cn.com.fintheircing.admin.common.entity.AdminClientInfo;
import cn.com.fintheircing.admin.proxy.dao.repository.IAdminClientInfoRepository;
import cn.com.fintheircing.admin.useritem.common.Status;
import cn.com.fintheircing.admin.useritem.dao.repository.TransactionSummaryRepository;
import cn.com.fintheircing.admin.useritem.entity.TransactionSummary;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoTest {
    @Resource
   private TransactionSummaryRepository iAdminClientInfoRepository;
    @Test
    public void test(){
        for (int i=0;i<5;i++) {
            TransactionSummary t =
                    new TransactionSummary(
                            "178","蒙奇丶D·路飞","M","日本",new Date(),"海贼王", 3);
            iAdminClientInfoRepository.save(t);

        }
    }
    @Test
    public void test1(){
        List<String> list=null;
        list.add("123");
        System.out.println(list);
    }
}
