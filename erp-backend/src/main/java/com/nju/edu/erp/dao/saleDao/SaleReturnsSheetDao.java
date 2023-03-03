package com.nju.edu.erp.dao.saleDao;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.sale.SaleReturnsSheetContentPO;
import com.nju.edu.erp.model.po.sale.SaleReturnsSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SaleReturnsSheetDao {
    /**
     * 获取最近一条销售退货单
     * @return 最近一条销售退货单
     */
    SaleReturnsSheetPO getLatest();

    /**
     * 存入一条销售退货单记录
     * @param toSave 一条销售退货单记录
     * @return 影响的行数
     */
    int save(SaleReturnsSheetPO toSave);

    /**
     * 把销售退货单上的具体内容存入数据库
     * @param SaleReturnsSheetContent 销售退货单上的具体内容
     */
    void saveBatch(List<SaleReturnsSheetContentPO> SaleReturnsSheetContent);

    /**
     * 返回所有销售退货单
     * @return 销售退货单列表
     */
    List<SaleReturnsSheetPO> findAll();

    /**
     * 根据state返回销售退货单
     * @param state 销售退货单状态
     * @return 销售退货单列表
     */
    List<SaleReturnsSheetPO> findAllByState(SaleReturnsSheetState state);

    /**
     * 根据 saleReturnsSheetId 找到条目， 并更新其状态为state
     * @param saleReturnsSheetId 销售退货单id
     * @param state 销售退货单状态
     * @return 影响的条目数
     */
    int updateState(String saleReturnsSheetId, SaleReturnsSheetState state);

    /**
     * 根据 saleReturnsSheetId 和 prevState 找到条目， 并更新其状态为state
     * @param saleReturnsSheetId 销售退货单id
     * @param prevState 销售退货单之前的状态
     * @param state 销售退货单状态
     * @return 影响的条目数
     */
    int updateStateV2(String saleReturnsSheetId, SaleReturnsSheetState prevState, SaleReturnsSheetState state);

    /**
     * 通过saleReturnsSheetId找到对应条目
     * @param saleReturnsSheetId 销售退货单id
     * @return
     */
    SaleReturnsSheetPO findOneById(String saleReturnsSheetId);

    /**
     * 通过saleReturnsSheetId找到对应的content条目
     * @param saleReturnsSheetId
     * @return
     */
    List<SaleReturnsSheetContentPO> findContentBySaleReturnsSheetId(String saleReturnsSheetId);

    /**
     * 用与选取符合条件的(经营历程列表
     * @param startTime
     * @param endTime
     * @param supplier
     * @param salesman
     * @return
     */
    List<SaleReturnsSheetPO> findSaleReturnsSheet(Date startTime, Date endTime, Integer supplier, String salesman);
}
