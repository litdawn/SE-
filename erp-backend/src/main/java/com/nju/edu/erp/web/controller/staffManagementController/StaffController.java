package com.nju.edu.erp.web.controller.staffManagementController;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.staff.StaffInformationVO;
import com.nju.edu.erp.service.staffBusiness.StaffAccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/staff")
public class StaffController {
    StaffAccountService staffAccountService;

    @Autowired
    public StaffController(StaffAccountService staffAccountService) {
        this.staffAccountService = staffAccountService;
    }


    /**
     * 人力资源人员登记入职员工信息，系统创建账户
     */
    @Authorized(roles = {Role.HR, Role.ADMIN})
    @PostMapping(value = "/staff-account-make")
    public Response makeStaffAccount(@RequestBody StaffInformationVO staffInformationVO)  {
        staffAccountService.creatStaffAccount(staffInformationVO);
        return Response.buildSuccess();
    }

    /**
     * 人力资源人员修改入职员工信息
     */
    @Authorized(roles = {Role.HR, Role.ADMIN})
    @PostMapping(value = "/staff-account-change")
    public Response changeStaffAccountInfo(@RequestBody StaffInformationVO staffInformationVO)  {
        staffAccountService.changeStaffInfo(staffInformationVO);
        return Response.buildSuccess();
    }

    /**
     * 查询所有员工信息
     */
    @Authorized(roles = {Role.HR, Role.ADMIN})
    @PostMapping(value = "/staff-account-show")
    public Response showStaffAccount()  {
        return Response.buildSuccess(staffAccountService.showAllStaffAccount());
    }


    @GetMapping(value = "/show-all-staff-id")
    public Response findAllStaffId()  {
        return Response.buildSuccess(staffAccountService.getAllStaffId());
    }
}
