package com.nju.edu.erp.web.controller.overallManagementController;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.service.overallManagementBusiness.BusinessConditionService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/business-condition")
public class BusinessConditionController {

    private final BusinessConditionService businessConditionService;

    @Autowired
    public BusinessConditionController(BusinessConditionService businessConditionService) {
        this.businessConditionService = businessConditionService;
    }

    /**
     * 根据时间得经营情况
     * 时间的变量格式为：yyyy-mm-dd
     */
    @GetMapping(value = "/business-condition-show")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    public Response showSaleStatus(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        return Response.buildSuccess(businessConditionService.getBusinessConditionSheet(startTime, endTime));
    }
}
