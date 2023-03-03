package com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl;

import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.TaxCalculation;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;

import java.math.BigDecimal;

public class GradeTaxCalculation implements TaxCalculation {
    public BigDecimal calculateTax(SalarySheetVO salarySheetVO){
        BigDecimal rawTotal=salarySheetVO.getBasicAmount().multiply(BigDecimal.valueOf(12));
        BigDecimal [] prePointArray={BigDecimal.valueOf(0),BigDecimal.valueOf(36000),BigDecimal.valueOf(144000),BigDecimal.valueOf(300000),BigDecimal.valueOf(420000),BigDecimal.valueOf(660000),BigDecimal.valueOf(960000)};
        BigDecimal [] postPointArray={BigDecimal.valueOf(36000),BigDecimal.valueOf(144000),BigDecimal.valueOf(300000),BigDecimal.valueOf(420000),BigDecimal.valueOf(660000),BigDecimal.valueOf(960000),rawTotal};
        double [] trigger={0.03,0.10,0.20,0.25,0.30,0.35,0.45};
        BigDecimal tax =BigDecimal.ZERO;
        for(int i=0;i<=6;i++){
            if(prePointArray[i].compareTo(rawTotal)<=0
            &&rawTotal.compareTo(postPointArray[i])<=0){
                tax= tax.add(rawTotal.subtract(prePointArray[i]).multiply(BigDecimal.valueOf(trigger[i])));
            }else if(prePointArray[i].compareTo(rawTotal)<=0
                    &&rawTotal.compareTo(postPointArray[i])>0){
                tax = tax.add(postPointArray[i].subtract(prePointArray[i]).multiply(BigDecimal.valueOf(trigger[i])));
            }
        }

        return tax;
    }
}
