package com.nju.edu.erp.dao.warehouseDao;

import com.nju.edu.erp.model.po.warehouse.WarehouseOutputSheetContentPO;

import java.util.List;

;

public interface WarehouseOutputSheetContentDao {
    /**
     * 按照商品id获取出库商品（存量>0）并按单价排序
     * @param pid
     * @return
     */
    List<WarehouseOutputSheetContentPO> findByPidOrderBySalePricePos(String pid);
}
