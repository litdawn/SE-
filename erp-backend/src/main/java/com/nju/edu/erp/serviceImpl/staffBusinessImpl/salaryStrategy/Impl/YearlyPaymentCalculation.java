package com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl;

import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.PaymentCalculation;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.TaxCalculation;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;

import java.math.BigDecimal;


public class YearlyPaymentCalculation implements PaymentCalculation {

    TaxCalculation taxCalculation;

    public YearlyPaymentCalculation(TaxCalculation taxCalculation){
        this.taxCalculation=taxCalculation;
    }

    public BigDecimal calculateRawAmount(SalarySheetVO salarySheetVO){
        BigDecimal rawAmount = BigDecimal.ZERO;
        rawAmount = salarySheetVO.getBasicAmount().multiply(BigDecimal.valueOf(12)).add(salarySheetVO.getPositionAmount());
        return  rawAmount;
    }

    @Override
    public BigDecimal calculateTax(SalarySheetVO salarySheetVO) {
        BigDecimal taxAmount = BigDecimal.ZERO;
        GradeTaxCalculation gradeTaxCalculation = new GradeTaxCalculation();
        taxAmount = gradeTaxCalculation.calculateTax(salarySheetVO);
        return taxAmount;
    }

    @Override
    public BigDecimal calculateFinalAmount(SalarySheetVO salarySheetVO) {
        BigDecimal finalAmount = BigDecimal.ZERO;
        //扣税加年终奖
        finalAmount = salarySheetVO.getRawAmount().subtract(salarySheetVO.getTaxAmount()).add(salarySheetVO.getAnnualBonus());
        return  finalAmount;
    }
}
