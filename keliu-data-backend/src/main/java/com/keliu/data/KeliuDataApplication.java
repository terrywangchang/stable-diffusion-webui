package com.keliu.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <h3>客流数据填报系统 - 启动类</h3>
 * <p>
 * 基于若依（Ruoyi）架构思路，采用 Spring Boot 2.7.x + MyBatis-Plus 技术栈。
 * 启用了异步任务、定时任务、事务管理。
 * </p>
 *
 * @author keliu-system
 * @version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.keliu.data.mapper")
@EnableScheduling
@EnableAsync
@EnableTransactionManagement
public class KeliuDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeliuDataApplication.class, args);
        System.out.println("\n================================================");
        System.out.println("  客流数据填报系统 启动成功!");
        System.out.println("  API 文档:  http://localhost:8080/api/doc.html");
        System.out.println("================================================");
    }
}
