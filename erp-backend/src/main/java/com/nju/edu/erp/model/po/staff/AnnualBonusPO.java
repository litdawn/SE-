package com.nju.edu.erp.model.po.staff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnualBonusPO {

    String staffId;

    /**
     * 年终奖金额
     */
    BigDecimal annualAmount;
}
