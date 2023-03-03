package com.nju.edu.erp.model.po.sale;

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
public class PromotionPO {
    /**
     * 该策略的id，随便写一个和以前不一样的
     */
    String id;

    /**
     *'代金券，赠品，折扣',
     */
    String kind;

    /**
     *'客户等级，总金额，商品组合'
     */
    String rule;

    /**
     * 客户等级（rule=客户等级时）
     */
    int level;

    /**
     * 总金额（rule=总金额时）
     */
    BigDecimal amount;

    /**
     *组合中第一个商品的id
     */
    String composition1Id;

    /**
     *组合中第二个商品的id
     */
    String composition2Id;
    /**
     * 生效开始时间
     */
    Date beginDate;
    /**
     * 生效结束时间
     */
    Date endDate;
    /**
     * kind = 折扣时
     */
    int discount;
    /**
     * kind= 代金券||rule =商品组合(商品组合这里是每*一份*组合的*降价*金额)
     */
    BigDecimal voucher;
    /**
     * kind = 赠品 时，赠品的商品id
     */
    String giftId;
    /**
     * kind = 赠品 时赠品数额
     */
    int giftQuantity;

}
