package com.nju.edu.erp.service.overallManagementBusiness;

import com.nju.edu.erp.model.vo.purchase.PayableSheetVO;
import com.nju.edu.erp.model.vo.sale.ReceivableSheetVO;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.sale.SaleReturnsSheetVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusinessProcessService {
    /**
     * 根据起始时间，结束时间，客户，业务员查询
     * @param
     * @return 销售单
     */
    List<SaleSheetVO> findSaleSheet(String startTime, String endTime, Integer supplier, String salesman);

    /**
     * 根据起始时间，结束时间，客户，业务员查询
     * @param
     * @return 销售退货单
     */
    List<SaleReturnsSheetVO> findSaleReturnsSheet(String startTime, String endTime, Integer supplier, String salesman);

    /**
     * 根据起始时间，结束时间，客户，业务员查询
     * @param
     * @return 进货单
     */
    List<PurchaseSheetVO> findPurchaseSheet(String startTime, String endTime, Integer supplier, String salesman);

    /**
     * 根据起始时间，结束时间，客户，业务员查询
     * @param
     * @return 进货退货单
     */
    List<PurchaseReturnsSheetVO> findPurchaseReturnsSheet(String startTime, String endTime, Integer supplier, String salesman);

    /**
     * 根据起始时间，结束时间，客户，业务员查询
     * @param
     * @return 收款单
     */
    List<ReceivableSheetVO> findReceivableSheet(String startTime, String endTime, Integer supplier, String salesman);

    /**
     * 根据起始时间，结束时间，客户，业务员查询
     * @param
     * @return 付款单
     */
    List<PayableSheetVO> findPayableSheet(String startTime, String endTime, Integer supplier, String salesman);

    /**
     * 根据起始时间，结束时间，客户，业务员查询
     * @param
     * @return 工资单
     */
    List<SalarySheetVO> findSalarySheet(String startTime, String endTime, Integer supplier, String salesman);
}
