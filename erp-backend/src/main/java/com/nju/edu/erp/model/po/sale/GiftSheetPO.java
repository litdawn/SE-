package com.nju.edu.erp.model.po.sale;


import com.nju.edu.erp.enums.sheetState.GiftSheetState;
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
public class GiftSheetPO {
    /**
     * 单据id
     */
    String id;

    /**
     * 对应的销售单id
     */
    String saleSheetId;

    /**
     * 创建时间
     */
    Date createTime;
    /**
     * 赠送商品id
     */
    String productId;

    /**
     * 赠送商品数量
     */
    int quantity;


    /**
     * 单据状态
     */
    GiftSheetState state;


}
