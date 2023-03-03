package com.nju.edu.erp.dao.saleDao;

import com.nju.edu.erp.model.po.sale.PromotionPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface PromotionDao {


    /**
     * 查找符合条件的最大的代金券
     * @param amount 总金额
     * @param date 使用时间
     * @return
     */
    PromotionPO findVoucherDESC(int level,BigDecimal amount, Date date);

    /**
     * 查找符合条件的商品组合降价策略
     * @param date 使用时间
     * @param sheetId sale sheet id
     * @return
     */
    List<PromotionPO> findCompositionDiscountASC(Date date,String sheetId);

    /**
     * 查找符合条件的最大折扣
     * @param level 客户等级
     * @param date 使用时间
     * @return
     */
    PromotionPO findDiscountASCByTimeAndLevel(int level,Date date);

    /**
     * 寻找符合条件的赠品策略,并选取对等级要求最严的一个
     * @param level 客户等级
     * @param date 时间
     * @return
     */
    PromotionPO findGiftByTimeAndLevel(int level,Date date);

    /**
     * 寻找符合条件的赠品策略，并选取对总金额要求最严格的一个
     * @param amount 总金额
     * @param date 时间
     * @return
     */
    PromotionPO findGiftByTimeAndAmount(BigDecimal amount,Date date);

    /**
     * 所有赠品策略
     * @return
     */
    List<PromotionPO> findAll();

    /**
     * 建立一条代金券策略
     * @param po
     */
    void addVoucher(PromotionPO po);

    /**
     * 建立一条折扣策略
     * @param po
     */
    void addDiscount(PromotionPO po);

    /**
     * 建立一条赠品策略
     */
    void addGift(PromotionPO po);
}
