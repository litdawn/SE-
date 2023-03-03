package com.nju.edu.erp.serviceImpl.saleBusinessImpl.promotionStrategy.Impl;

import com.nju.edu.erp.serviceImpl.saleBusinessImpl.promotionStrategy.PromotionStrategy;
import com.nju.edu.erp.dao.saleDao.PromotionDao;
import com.nju.edu.erp.dao.saleDao.SaleSheetDao;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.model.po.sale.PromotionPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CompositionDiscount implements PromotionStrategy {

    private final SaleSheetDao saleSheetDao;
    private final PromotionDao promotionDao;

    private BigDecimal voucher;

    @Autowired
    public CompositionDiscount(PromotionDao promotionDao,SaleSheetDao saleSheetDao){
        this.saleSheetDao=saleSheetDao;
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
        List<PromotionPO> promotionPOList = promotionDao.findCompositionDiscountASC(new Date(),saleSheetPO.getId());
        if(promotionPOList==null||promotionPOList.size()==0){
            saleSheetPO.setFinalAmount(saleSheetPO.getRawTotalAmount());
            saleSheetPO.setVoucherAmount(BigDecimal.ZERO);
            return saleSheetPO;
        }
        int quantity1 =0;
        int quantity2 =0;
        BigDecimal maxDiscount = BigDecimal.ZERO;
        PromotionPO used =new PromotionPO();
        for(PromotionPO po:promotionPOList){
            quantity1 = saleSheetDao.findProductQuantity(saleSheetPO.getId(),po.getComposition1Id());
            quantity2 = saleSheetDao.findProductQuantity(saleSheetPO.getId(),po.getComposition2Id());
            BigDecimal temp = BigDecimal.valueOf(Math.min(quantity1, quantity2)).multiply(po.getVoucher());
            if(maxDiscount.compareTo(temp)<0){
                used = po;
                maxDiscount = temp;
            }
        }
        this.voucher = maxDiscount;
        return applyThis(used,saleSheetPO);
    }

    /**
     * @param po
     * @param saleSheetPO
     * @return
     */
    @Override
    public SaleSheetPO applyThis(PromotionPO po, SaleSheetPO saleSheetPO) {
        saleSheetPO.setFinalAmount(saleSheetPO.getRawTotalAmount().subtract(voucher));
        saleSheetPO.setVoucherAmount(voucher);
        return saleSheetPO;
    }
}
