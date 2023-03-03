package com.nju.edu.erp.serviceImpl.saleBusinessImpl.promotionStrategy.Impl;

import com.nju.edu.erp.serviceImpl.saleBusinessImpl.promotionStrategy.PromotionStrategy;
import com.nju.edu.erp.dao.saleDao.GiftSheetDao;
import com.nju.edu.erp.dao.saleDao.PromotionDao;
import com.nju.edu.erp.enums.sheetState.GiftSheetState;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.model.po.sale.GiftSheetPO;
import com.nju.edu.erp.model.po.sale.PromotionPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class Gift implements PromotionStrategy {

    PromotionDao promotionDao;
    GiftSheetDao giftSheetDao;

    @Autowired
    public Gift(PromotionDao promotionDao, GiftSheetDao giftSheetDao){
        this.promotionDao=promotionDao;
        this.giftSheetDao=giftSheetDao;
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
        PromotionPO promotionPO1 = promotionDao.findGiftByTimeAndLevel(customerPO.getLevel(),new Date());
        if(promotionPO1!=null){
            return applyThis(promotionPO1,saleSheetPO);
        }
        PromotionPO po = promotionDao.findGiftByTimeAndAmount(saleSheetPO.getRawTotalAmount(),new Date());
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
        GiftSheetPO prevSheet = giftSheetDao.getLatest();
        String realSheetId = IdGenerator.generateSheetId(prevSheet == null ? null : prevSheet.getId(), "KCZSD");
        GiftSheetPO giftSheetPO = GiftSheetPO.builder()
                .id(realSheetId)
                .saleSheetId(saleSheetPO.getId())
                .createTime(new Date())
                .productId(po.getGiftId())
                .quantity(po.getGiftQuantity())
                .state(GiftSheetState.PENDING_LEVEL_2)
                .build();
        giftSheetDao.addSheet(giftSheetPO);
        return saleSheetPO;
    }


}
