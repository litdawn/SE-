package com.nju.edu.erp.serviceImpl.saleBusinessImpl.promotionStrategy;

import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.model.po.sale.PromotionPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import org.springframework.stereotype.Service;

@Service
public interface PromotionStrategy {

    /**
     *  是否符合该策略条件
     * @return
     */
    SaleSheetPO canThisStrategyBeApplied(CustomerPO customerPO, SaleSheetPO saleSheetPO);

    SaleSheetPO applyThis(PromotionPO po, SaleSheetPO saleSheetPO);


}
