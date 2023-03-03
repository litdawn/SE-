package com.nju.edu.erp.model.vo.sale;


import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
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
public class ReceivableSheetVO {
    /**
     * 收款单单据编号（格式为：SKD-yyyyMMdd-xxxxx), 新建单据时前端传null
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
     * 转账列表
     */
    List<ReceivableSheetContentVO> receivableContent;
    /**
     * 总额
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private ReceivableSheetState state;
}
