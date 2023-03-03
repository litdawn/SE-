package com.nju.edu.erp.stub;

import com.nju.edu.erp.model.po.staff.SalarySheetPO;

import java.math.BigDecimal;

public class SalaryCalculationStub {//用于代替具体的工资计算

    public SalarySheetPO fakeSalaryCalculation(SalarySheetPO po){
        if(po.getRawAmount().compareTo(BigDecimal.valueOf(1000.00))==0)
            po.setFinalAmount(BigDecimal.valueOf(800.00));
        else if(po.getRawAmount().compareTo(BigDecimal.valueOf(2000.00))==0)
            po.setFinalAmount(BigDecimal.valueOf(1700.00));
        else if(po.getRawAmount().compareTo(BigDecimal.valueOf(3000.00))==0)
            po.setFinalAmount(BigDecimal.valueOf(2600.00));
        else if(po.getRawAmount().compareTo(BigDecimal.valueOf(4000.00))==0)
            po.setFinalAmount(BigDecimal.valueOf(3500.00));
        else if(po.getRawAmount().compareTo(BigDecimal.valueOf(5000.00))==0)
            po.setFinalAmount(BigDecimal.valueOf(4400.00));
        return po;
    }
}
