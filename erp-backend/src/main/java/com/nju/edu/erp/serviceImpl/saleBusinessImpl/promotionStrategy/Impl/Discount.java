package com.nju.edu.erp.serviceImpl.saleBusinessImpl.promotionStrategy.Impl;

import com.nju.edu.erp.serviceImpl.saleBusinessImpl.promotionStrategy.PromotionStrategy;
import com.nju.edu.erp.dao.saleDao.PromotionDao;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.model.po.sale.PromotionPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class Discount implements PromotionStrategy {

    PromotionDao promotionDao;

    @Autowired
    public Discount(PromotionDao promotionDao){
        this.promotionDao=promotionDao;
    }

    /**
     * 是否符合该策略条件
     *
     * @param customerPO
     * @param saleSheetPO
     * @return
     */
    @Override
    public SaleSheetPO canThisStrategyBeApplied(CustomerPO customerPO, SaleSheetPO saleSheetPO) {
        PromotionPO promotionPO1 = promotionDao.findDiscountASCByTimeAndLevel(customerPO.getLevel(),new Date());
        if(promotionPO1!=null){
            return applyThis(promotionPO1,saleSheetPO);
        }
        saleSheetPO.setDiscount(BigDecimal.valueOf(1.0));
        return saleSheetPO;
    }

    @Override
    public SaleSheetPO applyThis(PromotionPO po, SaleSheetPO saleSheetPO) {
        saleSheetPO.setDiscount(BigDecimal.valueOf(po.getDiscount()/100.00));
        saleSheetPO.setFinalAmount(saleSheetPO.getFinalAmount().multiply(saleSheetPO.getDiscount()));

        return saleSheetPO;
    }
}
