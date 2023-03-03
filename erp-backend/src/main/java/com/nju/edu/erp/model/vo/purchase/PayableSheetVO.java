package com.nju.edu.erp.model.vo.purchase;


import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.model.vo.purchase.PayableSheetContentVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayableSheetVO {
    /**
     * 付款单单据编号（格式为：XJFYD-yyyyMMdd-xxxxx), 新建单据时前端传null
     */
    private String id;
    /**
     * 银行账户
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
     * 条目清单
     */
    List<PayableSheetContentVO> payableContent;
    /**
     * 总额
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private PayableSheetState state;
}
