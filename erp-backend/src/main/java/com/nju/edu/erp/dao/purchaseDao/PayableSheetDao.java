package com.nju.edu.erp.dao.purchaseDao;

import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.model.po.purchase.PayableSheetContentPO;
import com.nju.edu.erp.model.po.purchase.PayableSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface PayableSheetDao {
    /**
     * 存入一条付款单记录
     * @param toSave 一条付款单记录
     * @return 影响的行数
     */
    int save(PayableSheetPO toSave);
    /**
     * 把付款单上条目清单的存入数据库
     * @param payableSheetContentPO 付款单上的条目清单
     */
    void saveBatch(List<PayableSheetContentPO> payableSheetContentPO);
    /**
     * 获取最近一条付款单
     * @return 最近一条付款单
     */
    PayableSheetPO getLatest();


    int updateState(String payableSheetId, PayableSheetState state);


    PayableSheetPO findOneById(String payableSheetId);

    /**
     * 用与选取符合条件的(经营历程列表
     * @param startTime
     * @param endTime
     * @param supplier
     * @param salesman
     * @return
     */
    List<PayableSheetPO> findPayableSheet(Date startTime, Date endTime, Integer supplier, String salesman);

    List<PayableSheetPO> findAll();

    List<PayableSheetContentPO> findContentBySheetId(String sheetId);
}
