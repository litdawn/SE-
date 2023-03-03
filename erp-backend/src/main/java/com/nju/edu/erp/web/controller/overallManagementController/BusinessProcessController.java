package com.nju.edu.erp.web.controller.overallManagementController;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.service.overallManagementBusiness.BusinessProcessService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/business-process")
public class BusinessProcessController {

    private final BusinessProcessService businessProcessService;

    @Autowired
    public BusinessProcessController(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    /**
     * 根据条件得经营历程列表
     * 时间的变量格式为：yyyy-mm-dd
     */
    @GetMapping(value = "/business-process-show")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    public Response showSaleStatus(@RequestParam("sheetType") String sheetType,@RequestParam("startTime") String startTime,
                                   @RequestParam("endTime") String endTime, @RequestParam("supplier") Integer supplier,
                                   @RequestParam("salesman") String salesman) {
        switch(sheetType){
            case "销售单" :
                return Response.buildSuccess(businessProcessService.findSaleSheet(startTime, endTime, supplier, salesman));
            case "销售退货单" :
                return Response.buildSuccess(businessProcessService.findSaleReturnsSheet(startTime, endTime, supplier, salesman));
            case "进货单" :
                return Response.buildSuccess(businessProcessService.findPurchaseSheet(startTime, endTime, supplier, salesman));
            case "进货退货单" :
                return Response.buildSuccess(businessProcessService.findPurchaseReturnsSheet(startTime, endTime, supplier, salesman));
            case "付款单" :
                return Response.buildSuccess(businessProcessService.findPayableSheet(startTime, endTime, supplier, salesman));
            case "收款单" :
                return Response.buildSuccess(businessProcessService.findReceivableSheet(startTime, endTime, supplier, salesman));
            case "工资单" :
                return Response.buildSuccess(businessProcessService.findSalarySheet(startTime, endTime, supplier, salesman));
        }
        return null;
    }
}
