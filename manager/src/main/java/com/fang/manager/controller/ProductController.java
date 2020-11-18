package com.fang.manager.controller;

import com.fang.entity.Product;
import com.fang.manager.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 *产品
 * @author fang
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static Logger LOG = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService service;

    @PostMapping()
    public Product addProduct(@RequestBody Product product){
        LOG.info("创建产品,参数={}",product);
        Product product1 = service.addProduct(product);
        LOG.info("创建产品,结果={}",product1);
        return product1;
    }
    @GetMapping("/{id}")
    public Product findOne(@PathVariable String id){
        LOG.info("查询单个产品,参数={}",id);
        Product result = service.findOne(id);
        LOG.info("查询单个产品,结果={}",result);
        return result;
    }

    @GetMapping()
    public Page<Product> query(String ids,
                               BigDecimal minRewardRate,
                               BigDecimal maxRewardRate,
                               String status,
                               @RequestParam(defaultValue = "0") int pageNum,
                               @RequestParam(defaultValue = "10") int pageSize){
        LOG.info("查询产品,ids={},minRewardRate={},maxRewardRate={},status={},pageNum={},pageSize={}"
                , ids, minRewardRate, maxRewardRate, status , pageNum,pageSize);
        List<String> idList = null,statusList = null;
        if (!StringUtils.isEmpty(ids)){
            idList = Arrays.asList(ids.split(","));
        }
        if (!StringUtils.isEmpty(status)){
            statusList = Arrays.asList(status.split(","));
        }
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Product> page = service.query(idList, minRewardRate, maxRewardRate, statusList, pageable);
        LOG.info("查询单个产品,结果={}",page);
        return page;
    }

}
