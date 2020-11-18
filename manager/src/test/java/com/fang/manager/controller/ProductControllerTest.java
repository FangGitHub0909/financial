package com.fang.manager.controller;


import com.fang.entity.Product;
import com.fang.entity.enums.ProductStatus;
import com.fang.util.RestUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private static RestTemplate rest = new RestTemplate();
    //local.server.port获取到的是RANDOM_PORT
    @Value("http://localhost:${local.server.port}/manager")
    private String baseUrl;

    //正常产品数据
    private static List<Product> normals = new ArrayList<>();
    //异常数据列表
    private static List<Product> exceptions = new ArrayList<>();

    /**
     * 初始化数据
     */
    @BeforeClass
    public static void init(){
        Product product1 = new Product("T001", "灵活宝1号", ProductStatus.AUDITING.name(), BigDecimal.valueOf(11), BigDecimal.valueOf(1), BigDecimal.valueOf(3.42));
        Product product2 = new Product("T002", "灵活宝2号", ProductStatus.AUDITING.name(), BigDecimal.valueOf(12), BigDecimal.valueOf(2), BigDecimal.valueOf(4.42));
        Product product3 = new Product("T003", "灵活宝3号", ProductStatus.AUDITING.name(), BigDecimal.valueOf(13), BigDecimal.valueOf(3), BigDecimal.valueOf(5.42));
        normals.add(product1);
        normals.add(product2);
        normals.add(product3);

        Product product4 = new Product(null, "编号为空", ProductStatus.AUDITING.name(), BigDecimal.valueOf(11), BigDecimal.valueOf(1), BigDecimal.valueOf(3.42));
        Product product5 = new Product("E002", "收益率大于31", ProductStatus.AUDITING.name(), BigDecimal.ZERO, BigDecimal.valueOf(2), BigDecimal.valueOf(31));
        Product product6 = new Product("E003", "投资步长为小数", ProductStatus.AUDITING.name(), BigDecimal.ZERO, BigDecimal.valueOf(1.01), BigDecimal.valueOf(3.42));
        exceptions.add(product4);
        exceptions.add(product5);
        exceptions.add(product6);
        //自定义错误处理
        ResponseErrorHandler errorHandler =  new ResponseErrorHandler(){
            //关闭错误提醒
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        };
        //Rest添加自定义错误的方法
        rest.setErrorHandler(errorHandler);

    }

    /**
     *  测试数据是否能够正常插入
     */
    @Test
    public void create(){
        normals.forEach((product)->{
            Product p  = (Product) RestUtil.postJson(rest, baseUrl + "/products", product, Product.class);
            Assert.notNull(p.getCreateAt(),"插入失败");
        });
    }
    /**
     *  错误测试
     */
    @Test
    public void createException(){
        exceptions.forEach((product)->{
            Map<String,String> map  = (Map<String, String>) RestUtil.postJson(rest, baseUrl + "/products", product, HashMap.class);
            System.out.println(map);
        });
    }
}
