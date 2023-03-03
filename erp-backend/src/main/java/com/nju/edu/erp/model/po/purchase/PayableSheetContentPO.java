package com.nju.edu.erp.model.po.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayableSheetContentPO {
    /**
     * 关联的sheetId
     */
    private String sheetId;
    /**
     * 条目名
     */
    private String name;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
}
