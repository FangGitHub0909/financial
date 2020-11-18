package com.fang.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.Entity;

/**
 * @author fang
 */
@EnableSwagger2
@SpringBootApplication
@EntityScan(basePackages = "com.fang.entity")
public class ManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class,args);
    }
}
