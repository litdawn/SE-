package com.nju.edu.erp.model.po.overallManagement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessConditionSheetPO {
    /**
     * 折让后总收入
     */
    private BigDecimal finalAmount;
    /**
     * 折让(discount+voucherAmount)
     */
    private BigDecimal discountAmount;
    /**
     * 工资+进货totalAmount
     */
    private BigDecimal expenditureAmount;
    /**
     * 利润
     */
    private BigDecimal profit;
}
