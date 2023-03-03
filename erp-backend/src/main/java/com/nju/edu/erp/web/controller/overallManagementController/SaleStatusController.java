package com.nju.edu.erp.web.controller.overallManagementController;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.service.overallManagementBusiness.SaleStatusService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path = "/sale-status")
public class SaleStatusController {

    private final SaleStatusService saleStatusService;

    @Autowired
    public SaleStatusController(SaleStatusService saleStatusService) {
        this.saleStatusService = saleStatusService;
    }

    /**
     * 根据条件得销售清单列表
     * 时间的变量格式为：yyyy-mm-dd
     */
    @GetMapping(value = "/sale-status-show")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    public Response showSaleStatus(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime,
                                   @RequestParam("name") String name, @RequestParam("supplier") Integer supplier,
                                   @RequestParam("salesman") String salesman) {
        return Response.buildSuccess(saleStatusService.getSaleStatusSheet(startTime, endTime, name, supplier, salesman));
    }
}
