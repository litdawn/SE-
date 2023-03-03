package com.nju.edu.erp.service.overallManagementBusiness;


import com.nju.edu.erp.model.vo.overallManagement.SaleStatusSheetVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SaleStatusService {

    /**
     * 根据起始时间，结束时间，商品名，客户，业务员查询
     * @param
     * @return 销售明细列表
     */
    List<SaleStatusSheetVO> getSaleStatusSheet(String startTime, String endTime, String name, Integer supplier, String salesman);
}
