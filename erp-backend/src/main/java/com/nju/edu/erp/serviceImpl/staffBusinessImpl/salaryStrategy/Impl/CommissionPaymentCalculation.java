package com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl;

import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.PaymentCalculation;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.TaxCalculation;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class CommissionPaymentCalculation implements PaymentCalculation {

    TaxCalculation taxCalculation;

    public CommissionPaymentCalculation(TaxCalculation taxCalculation){
        this.taxCalculation=taxCalculation;
    }

    public BigDecimal calculateRawAmount(SalarySheetVO salarySheetVO){
        BigDecimal rawAmount = BigDecimal.ZERO;
        rawAmount = salarySheetVO.getBasicAmount().add(salarySheetVO.getPositionAmount());
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
        //raw*(1+提成) 减（税+缺勤）加年终
        finalAmount = salarySheetVO.getRawAmount().multiply(BigDecimal.ONE.add(BigDecimal.valueOf(salarySheetVO.getPushMoney()))).subtract
                        (salarySheetVO.getTaxAmount().add(BigDecimal.valueOf(salarySheetVO.getAbsenceTimes()).
                                multiply(salarySheetVO.getBasicAmount().divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_DOWN)))).
                add(salarySheetVO.getAnnualBonus());
        return  finalAmount;
    }
}
