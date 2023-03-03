package com.nju.edu.erp.model.po.sale;


import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivableSheetPO {
    /**
     * 收款单单据编号（格式为：SKD-yyyyMMdd-xxxxx
     */
    private String id;
//    /**
//     * 供应商\销售商
//     */
//    private CustomerType type;
    /**
     * 客户id
     */
    private Integer customerId;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 总额
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态
     */
    private ReceivableSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
