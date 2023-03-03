package com.nju.edu.erp.service.staffBusiness;

import java.math.BigDecimal;

public interface AnnualBonusService {
    /**
     * 设置年终奖
     * @param staffId
     * @param annualAmount
     */
    void setAnnualAmount(String staffId, BigDecimal annualAmount);

    /**
     * 根据员工id查找年终奖
     * @param staffId
     * @return
     */
    BigDecimal findAnnualAmountById(String staffId);
}
