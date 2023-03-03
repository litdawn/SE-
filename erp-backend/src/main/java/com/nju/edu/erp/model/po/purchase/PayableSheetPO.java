package com.nju.edu.erp.model.po.purchase;


import com.nju.edu.erp.enums.sheetState.PayableSheetState;
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
public class PayableSheetPO {
    /**
     * 付款单单据编号（格式为：XJFYD-yyyyMMdd-xxxxx), 新建单据时前端传null
     */
    private String id;
    /**
     * 银行账户id
     */
    private String bankAccount;
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
    private PayableSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
