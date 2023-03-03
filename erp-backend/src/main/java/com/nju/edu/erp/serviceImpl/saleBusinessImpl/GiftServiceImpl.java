package com.nju.edu.erp.serviceImpl.saleBusinessImpl;

import com.nju.edu.erp.dao.saleDao.GiftSheetDao;
import com.nju.edu.erp.enums.sheetState.GiftSheetState;
import com.nju.edu.erp.model.po.sale.GiftSheetPO;
import com.nju.edu.erp.service.saleBusiness.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GiftServiceImpl implements GiftService {

    private final GiftSheetDao giftSheetDao;

    @Autowired
    GiftServiceImpl(GiftSheetDao giftSheetDao){
        this.giftSheetDao = giftSheetDao;
    }
    /**
     * 根据单据状态获取销售单
     *
     * @param state
     * @return
     */
    @Override
    public List<GiftSheetPO> getGiftSheetByState(GiftSheetState state) {
        return giftSheetDao.findAllByState(state);
    }

    /**
     * 审批单据
     *
     * @param giftSheetId
     * @param state
     */
    @Override
    @Transactional
    public void approval(String giftSheetId, GiftSheetState state) {
        giftSheetDao.updateState(giftSheetId,state);
    }

    /**
     * 根据销售单Id搜索销售单信息
     *
     * @param giftSheetId 销售单Id
     * @return 销售单全部信息
     */
    @Override
    public GiftSheetPO getGiftSheetById(String giftSheetId) {
        return giftSheetDao.findOneById(giftSheetId);
    }
}
