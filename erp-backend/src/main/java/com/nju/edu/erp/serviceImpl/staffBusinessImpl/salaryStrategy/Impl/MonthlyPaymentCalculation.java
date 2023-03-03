package com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl;

import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.PaymentCalculation;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.TaxCalculation;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MonthlyPaymentCalculation implements PaymentCalculation {
    TaxCalculation taxCalculation;

    public MonthlyPaymentCalculation(TaxCalculation taxCalculation){
        this.taxCalculation=taxCalculation;
    }

    public BigDecimal calculateRawAmount(SalarySheetVO salarySheetVO){
        BigDecimal rawAmount = BigDecimal.ZERO;
        rawAmount = salarySheetVO.getBasicAmount().add(salarySheetVO.getPositionAmount());
        return  rawAmount;
    }

    @Override
    public BigDecimal calculateTax(SalarySheetVO salarySheetVO) {
        return taxCalculation.calculateTax(salarySheetVO);
    }

    @Override
    public BigDecimal calculateFinalAmount(SalarySheetVO salarySheetVO) {
        BigDecimal finalAmount = BigDecimal.ZERO;
        //raw减（税+缺勤）加年终
        finalAmount = salarySheetVO.getRawAmount().subtract
                        (salarySheetVO.getTaxAmount().add(BigDecimal.valueOf(salarySheetVO.getAbsenceTimes()).
                        multiply(salarySheetVO.getBasicAmount().divide(BigDecimal.valueOf(30),2, RoundingMode.HALF_DOWN)))).
                add(salarySheetVO.getAnnualBonus());
        return  finalAmount;
    }
}
