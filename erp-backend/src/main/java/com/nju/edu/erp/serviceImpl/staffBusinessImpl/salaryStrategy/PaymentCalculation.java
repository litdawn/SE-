package com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy;

import com.nju.edu.erp.model.vo.staff.SalarySheetVO;

import java.math.BigDecimal;


public interface PaymentCalculation {

    public BigDecimal calculateRawAmount(SalarySheetVO salarySheetVO);

    public BigDecimal calculateTax(SalarySheetVO salarySheetVO);

    public BigDecimal calculateFinalAmount(SalarySheetVO salarySheetVO);


}

