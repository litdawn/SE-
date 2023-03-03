package com.nju.edu.erp.web.controller.staffManagementController;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.dao.staffDao.SalarySheetDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.SalaryRule;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.service.staffBusiness.AnnualBonusService;
import com.nju.edu.erp.service.staffBusiness.SalaryService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping(path = "/salary")
public class SalaryController {
    SalaryService salaryService;

    AnnualBonusService annualBonusService;


    @Autowired
    public SalaryController(SalaryService salaryService, AnnualBonusService annualBonusService) {
        this.salaryService = salaryService;
        this.annualBonusService = annualBonusService;
    }


    /**
     * 人力资源人员制定工资单
     */
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(UserVO userVO, @RequestBody SalarySheetVO salarySheetVO)  {
        salaryService.makeSalarySheet(userVO, salarySheetVO);
        return Response.buildSuccess();
    }

    /**
     * 总经理审批
     * @param salarySheetId 工资单ID
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("SalarySheetId") String salarySheetId,
                                   @RequestParam("state") SalarySheetState state)  {
        if(state.equals(SalarySheetState.FAILURE) || state.equals(SalarySheetState.SUCCESS)) {
            salaryService.approval(salarySheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看工资单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) SalarySheetState state)  {
        return Response.buildSuccess(salaryService.getSalarySheetByState(state));
    }

    /**
     * 根据工资单Id搜索工资单信息
     * @param id 工资单Id
     * @return 工资单全部信息
     */
    @GetMapping(value = "/find-sheet")
    public Response findBySheetId(@RequestParam(value = "id") String id)  {
        return Response.buildSuccess(salaryService.getSalarySheetById(id));
    }

    /**
     * 人力资源人员制定薪酬规则
     * @param staffId 职工id
     * @param rule 提成制/月薪制/年薪制
     */
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    @GetMapping(value = "/salaryRuleChange")
    public Response makeSalaryRule(@RequestParam("staffId") String staffId,
                                   @RequestParam("rule")SalaryRule rule)  {
        salaryService.makeSalaryRule(staffId, rule);
        return Response.buildSuccess();
    }

    /**
     * 总经理查看前11个月该员工薪资总和
     * @param staffId 职工id
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/prev-salary")
    public Response showPrevSalary(@RequestParam("staffId") String staffId)  {
        return Response.buildSuccess(salaryService.checkPrevSalary(staffId));
    }

    /**
     * 总经理设置年终奖
     * @param staffId 职工id
     * @param annualAmount
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/set-annual-bonus")
    public Response makeAnnualAmount(@RequestParam("staffId") String staffId,
                                   @RequestParam("annualAmount")BigDecimal annualAmount)  {
        annualBonusService.setAnnualAmount(staffId, annualAmount);
        return Response.buildSuccess();
    }

    /**
     * 根据id得到年终奖
     * @param staffId 职工id
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/get-annual-bonus")
    public Response getAnnualAmount(@RequestParam("staffId") String staffId)  {
        return Response.buildSuccess(annualBonusService.findAnnualAmountById(staffId));
    }


    /**
     * 所有工资单信息
     */
    @GetMapping(value = "/show-all-sheet")
    public Response findAllAnnualBonus()  {
        return Response.buildSuccess(salaryService.findAllSheet());
    }
}
