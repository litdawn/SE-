package com.nju.edu.erp.dao.saleDao;

import com.nju.edu.erp.enums.sheetState.GiftSheetState;
import com.nju.edu.erp.model.po.sale.GiftSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface GiftSheetDao {
    /**
     * 获取最近一条库存赠送单
     */
    GiftSheetPO getLatest();

    /**
     *  根据id寻找
     * @param giftSheetId
     * @return
     */
    GiftSheetPO findOneById(String giftSheetId);

    BigDecimal findPriceById(String id);

    /**
     * 添加一个库存赠送单
     * @param giftSheetPO
     */
    void addSheet(GiftSheetPO giftSheetPO);

    /**
     * 审批
     * @param id
     */
    void updateState(String id, GiftSheetState state);


    /**
     * 查询所有库存赠送单
     * @return
     */
    List<GiftSheetPO> findAll();

    List<GiftSheetPO> findAllByState(GiftSheetState state);

}
