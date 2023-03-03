package com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy;

import com.nju.edu.erp.model.vo.staff.SalarySheetVO;

import java.math.BigDecimal;


public class SalaryCalculation {
    SalarySheetVO _salarySheetVO;
    PaymentCalculation pc;

    public SalaryCalculation(SalarySheetVO salarySheetVO){
        _salarySheetVO = salarySheetVO;
    }

    public void setPaymentCalculation(PaymentCalculation paymentCalculation){
        pc = paymentCalculation;
    }

    public BigDecimal getRawAmount(){
        BigDecimal rawAmount = BigDecimal.ZERO;
        rawAmount = pc.calculateRawAmount(_salarySheetVO);
        return rawAmount;
    }

    public BigDecimal getTaxAmount(){
        BigDecimal taxAmount = BigDecimal.ZERO;
        taxAmount = pc.calculateTax(_salarySheetVO);
        return taxAmount;
    }

    public BigDecimal getFinalAmount(){
        BigDecimal finalAmount = BigDecimal.ZERO;
        finalAmount = pc.calculateFinalAmount(_salarySheetVO);
        return finalAmount;
    }

}
