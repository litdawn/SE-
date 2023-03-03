package com.nju.edu.erp.serviceImpl.saleBusinessImpl.promotionStrategy.Impl;

import com.nju.edu.erp.serviceImpl.saleBusinessImpl.promotionStrategy.PromotionStrategy;
import com.nju.edu.erp.dao.saleDao.PromotionDao;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.model.po.sale.PromotionPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class Voucher implements PromotionStrategy {

    PromotionDao promotionDao;

    @Autowired
    public Voucher(PromotionDao promotionDao){
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
        PromotionPO po = promotionDao.findVoucherDESC(customerPO.getLevel(),saleSheetPO.getRawTotalAmount(),new Date());
        if(po!=null){
            return applyThis(po,saleSheetPO);
        }
        return saleSheetPO;
    }

    /**
     * @param po
     * @param saleSheetPO
     * @return
     */
    @Override
    public SaleSheetPO applyThis(PromotionPO po, SaleSheetPO saleSheetPO) {
        saleSheetPO.setVoucherAmount(po.getVoucher().add(saleSheetPO.getVoucherAmount()));
        saleSheetPO.setFinalAmount(saleSheetPO.getFinalAmount().subtract(po.getVoucher()));
        return saleSheetPO;
    }
}
