package com.nju.edu.erp.dao.saleDao;


import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.po.sale.ReceivableSheetContentPO;
import com.nju.edu.erp.model.po.sale.ReceivableSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface ReceivableSheetDao {
    /**
     * 存入一条收款单记录
     * @param toSave 一条收款单记录
     * @return 影响的行数
     */
    int save(ReceivableSheetPO toSave);
    /**
     * 把收款单上转账列表的存入数据库
     * @param receivableSheetContentPO 收款单上的转账列表
     */
    void saveBatch(List<ReceivableSheetContentPO> receivableSheetContentPO);
    /**
     * 获取最近一条收款单
     * @return 最近一条收款单
     */
    ReceivableSheetPO getLatest();


    int updateState(String receivableSheetId, ReceivableSheetState state);


    ReceivableSheetPO findOneById(String receivableSheetId);

    /**
     * 用与选取符合条件的(经营历程列表
     * @param startTime
     * @param endTime
     * @param supplier
     * @param salesman
     * @return
     */
    List<ReceivableSheetPO> findReceivableSheet(Date startTime, Date endTime, Integer supplier, String salesman);

    List<ReceivableSheetPO> findAll();

    List<ReceivableSheetContentPO> findContentBySheetId(String sheetId);
}
