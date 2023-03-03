package com.nju.edu.erp.service.saleBusiness;

import com.nju.edu.erp.model.po.sale.PromotionPO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PromotionStrategyService {
    /**
     * 制订代金券策略
     * @param po
     */
    void addVoucher(PromotionPO po);

    /**
     * 制订赠送策略
     * @param po
     */
    void addGift(PromotionPO po);

    /**
     * 制订折扣策略
     * @param po
     */
    void addDiscount(PromotionPO po);

    List<PromotionPO> findAll();
}
