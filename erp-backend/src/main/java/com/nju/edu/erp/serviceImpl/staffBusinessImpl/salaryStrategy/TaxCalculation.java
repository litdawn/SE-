package com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy;

import com.nju.edu.erp.model.vo.staff.SalarySheetVO;

import java.math.BigDecimal;

public interface TaxCalculation {

    public BigDecimal calculateTax(SalarySheetVO salarySheetVO);

}


