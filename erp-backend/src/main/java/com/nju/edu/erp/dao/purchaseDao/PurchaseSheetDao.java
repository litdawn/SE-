package com.nju.edu.erp.dao.purchaseDao;

import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.po.purchase.PurchaseSheetContentPO;
import com.nju.edu.erp.model.po.purchase.PurchaseSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface PurchaseSheetDao {
    /**
     * 获取最近一条进货单
     * @return 最近一条进货单
     */
    PurchaseSheetPO getLatest();

    /**
     * 存入一条进货单记录
     * @param toSave 一条进货单记录
     * @return 影响的行数
     */
    int save(PurchaseSheetPO toSave);

    /**
     * 把进货单上的具体内容存入数据库
     * @param purchaseSheetContent 进货单上的具体内容
     */
    void saveBatch(List<PurchaseSheetContentPO> purchaseSheetContent);

    /**
     * 返回所有进货单
     * @return 进货单列表
     */
    List<PurchaseSheetPO> findAll();

    /**
     * 根据state返回进货单
     * @param state 进货单状态
     * @return 进货单列表
     */
    List<PurchaseSheetPO> findAllByState(PurchaseSheetState state);

    /**
     * 根据时间返回进货单
     * @param startTime,endTime
     * @return 进货列表
     */
    List<PurchaseSheetPO> findAllByTime(Date startTime, Date endTime);

    int updateState(String purchaseSheetId, PurchaseSheetState state);

    int updateStateV2(String purchaseSheetId, PurchaseSheetState prevState, PurchaseSheetState state);

    PurchaseSheetPO findOneById(String purchaseSheetId);

    List<PurchaseSheetContentPO> findContentByPurchaseSheetId(String purchaseSheetId);

    /**
     * 用与选取符合条件的(经营历程列表
     * @param startTime
     * @param endTime
     * @param supplier
     * @param salesman
     * @return
     */
    List<PurchaseSheetPO> findPurchaseSheet(Date startTime, Date endTime, Integer supplier, String salesman);
}
