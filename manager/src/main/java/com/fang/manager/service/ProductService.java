package com.fang.manager.service;

import com.fang.entity.Product;
import com.fang.entity.enums.ProductStatus;
import com.fang.manager.error.ErrorEnum;
import com.fang.manager.repositories.ProductRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 */
@Service
public class ProductService {

    private static Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    /**
     * 添加产品
     *
     * @param product
     * @return
     */
    public Product addProduct(Product product) {
        LOG.debug("创建产品,参数:{}", product);
        //数据校验
        checkProduct(product);
        //设置默认值
        setDefault(product);
        //保存
        Product result = repository.save(product);
        LOG.debug("创建产品,结果:{}", result);
        return result;
    }

    /**
     * 设置默认值
     * 创建时间更新时间
     * 投资步长，锁定期
     *
     * @param product
     */
    private void setDefault(Product product) {
        if (product.getCreateAt() == null) {
            product.setCreateAt(new Date());
        }
        if (product.getUpdateAt() == null) {
            product.setUpdateAt(new Date());
        }
        if (product.getStepAmount() == null) {
            product.setStepAmount(BigDecimal.ZERO);
        }
        if (product.getLockTerm() == null) {
            product.setLockTerm(0);
        }
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.AUDITING.name());
        }
    }

    /**
     * 数据产品校验
     * 1.非空数据
     * 2.收益率要0-30以内
     * 3.投资步长需要为整数
     *
     * @param product
     */
    private void checkProduct(Product product) {
        //数据校验
        //校验ID
        Assert.notNull(product.getId(), ErrorEnum.ID_NOT_NULL.getCode());
        //校验收益率
        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate()) < 0
                && BigDecimal.valueOf(30).compareTo(product.getRewardRate()) >= 0, "收益率范围错误");
        //校验投资步长
        Assert.isTrue(BigDecimal.valueOf(product.getStepAmount().longValue())
                .compareTo(product.getStepAmount()) == 0, "投资步长需要为整数");
        //longValue()是Long类的一个方法，用来得到Long类中的数值；也就是将包装类中的数据拆箱成基本数据类型。
    }

    /**
     * 查询单个的商品信息
     *
     * @param id
     * @return
     */
    public Product findOne(String id) {
        Assert.notNull(id, "需要产品编号参数");
        LOG.debug("查询单个产品，id={}", id);
        Product result = repository.getOne(id);
        LOG.debug("查询单个产品,result={}", result);
        return result;
    }

    /**
     * 分页查询
     */
    public Page<Product> query(List<String> idList,
                               BigDecimal minRewardRate,
                               BigDecimal maxRewardRate,
                               List<String> statusList,
                               Pageable pageable) {
        LOG.debug("查询产品,idList={},minRewardRate={},maxRewardRate={},statusList={},pageable={}"
                , idList, minRewardRate, maxRewardRate, statusList, pageable);
        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                Expression<String> idCol = root.get("id");
                Expression<BigDecimal> rewardRateCol = root.get("rewardRate");
                Expression<String> statusCol = root.get("status");
                List<Predicate> predicates = new ArrayList<>();
                if (idList != null && idList.size() > 0) {
                    predicates.add(idCol.in(idList));
                }
                if (BigDecimal.ZERO.compareTo(minRewardRate) < 0) {
                    predicates.add(criteriaBuilder.ge(rewardRateCol, minRewardRate));
                    //ge ：greater than or equal（大于或等于）
                }
                if (BigDecimal.ZERO.compareTo(maxRewardRate) < 0) {
                    predicates.add(criteriaBuilder.le(rewardRateCol, maxRewardRate));
                    //le  ：less than or equal（小于或等于）
                }
                if (statusList != null && statusList.size() > 0) {
                    predicates.add(statusCol.in(statusList));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[0]));

                return null;
            }
        };
        Page<Product> page = repository.findAll(specification, pageable);
        LOG.debug("查询产品，结果={}",page);
        return page;
    }
}
