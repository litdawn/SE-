package com.nju.edu.erp.service.saleBusiness;

import com.nju.edu.erp.enums.sheetState.GiftSheetState;
import com.nju.edu.erp.model.po.sale.GiftSheetPO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GiftService {


    /**
     * 根据单据状态获取库存赠送单
     * @param state
     * @return
     */
    List<GiftSheetPO> getGiftSheetByState(GiftSheetState state);

    /**
     * 审批单据
     * @param giftSheetId
     * @param state
     */
    void approval(String giftSheetId, GiftSheetState state);


    /**
     * 根据销售单Id搜索库存赠送单信息
     * @param giftSheetId Id
     * @return 库存赠送单全部信息
     */
    GiftSheetPO getGiftSheetById(String giftSheetId);
}
