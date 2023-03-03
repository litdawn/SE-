package com.nju.edu.erp.stub;

import com.nju.edu.erp.model.po.sale.GiftSheetPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import com.nju.edu.erp.model.po.staff.SalarySheetPO;
import com.nju.edu.erp.service.saleBusiness.GiftService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class PromotionStrategyStub {

    public SaleSheetPO fakePromotionApply(SaleSheetPO po){
        if(po.getRawTotalAmount().compareTo(BigDecimal.valueOf(2000.00))==0){
            po.setFinalAmount(BigDecimal.valueOf(1800.00));
            po.setVoucherAmount(BigDecimal.valueOf(200.00));
        }
        else if(po.getRawTotalAmount().compareTo(BigDecimal.valueOf(3000.00))==0){
            po.setFinalAmount(BigDecimal.valueOf(2700.00));
            po.setDiscount(BigDecimal.valueOf(0.9));
        }
        else if(po.getRawTotalAmount().compareTo(BigDecimal.valueOf(4000.00))==0){
            po.setFinalAmount(BigDecimal.valueOf(3400.00));
            po.setDiscount(BigDecimal.valueOf(0.9));
            po.setVoucherAmount(BigDecimal.valueOf(200.00));
        }
        else if(po.getRawTotalAmount().compareTo(BigDecimal.valueOf(4000.00))==0){
            po.setFinalAmount(BigDecimal.valueOf(3400.00));
            po.setDiscount(BigDecimal.valueOf(0.9));
            po.setVoucherAmount(BigDecimal.valueOf(200.00));
        }
        else if(po.getRawTotalAmount().compareTo(BigDecimal.valueOf(5000.00))==0){
//            GiftSheetPO giftSheetPO=GiftSheetPO.builder()
//                    .saleSheetId(po.getId())
//                    .createTime(new Date()).build();
            po.setDiscount(BigDecimal.valueOf(0.9));
            po.setVoucherAmount(BigDecimal.valueOf(500.00));
            po.setFinalAmount(BigDecimal.valueOf(4000.00));
        }
        return po;
    }
}
