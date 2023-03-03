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
public class CompanyBankAccountPO {
    /**
     * id
     */
    private String id;
    /**
     * 账户名称
     */
    private String companyBankAccountName;
    /**
     * 账户金额
     */
    private BigDecimal companyBankAccountMoney;
}
