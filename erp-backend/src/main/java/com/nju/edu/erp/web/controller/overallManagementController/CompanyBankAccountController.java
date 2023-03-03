package com.nju.edu.erp.web.controller.overallManagementController;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.overallManagement.CompanyBankAccountVO;
import com.nju.edu.erp.service.overallManagementBusiness.CompanyBankAccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/company-bank-account-management")
public class CompanyBankAccountController {
    CompanyBankAccountService companyBankAccountService;

    @Autowired
    public CompanyBankAccountController(CompanyBankAccountService companyBankAccountService) {
        this.companyBankAccountService = companyBankAccountService;
    }


    /**
     * 增加账户
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF,Role.ADMIN})
    @PostMapping(value = "/create-company-bank-account")
    public Response createBankAccount(@RequestBody CompanyBankAccountVO companyBankAccountVO)  {
        companyBankAccountService.createCompanyBankAccount(companyBankAccountVO);
        return Response.buildSuccess();
    }


    /**
     * 删除账户
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @DeleteMapping(value = "/delete-company-bank-account")
    public Response deleteBankAccount(@RequestParam(value = "id") String id)  {
        companyBankAccountService.deleteCompanyBankAccount(id);
        return Response.buildSuccess();
    }


    /**
     * 修改账户名
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/change-company-bank-account-name")
    public Response changeBankAccountName(@RequestBody CompanyBankAccountVO companyBankAccountVO)  {
        companyBankAccountService.changeCompanyBankAccountName(companyBankAccountVO);
        return Response.buildSuccess();
    }

    /**
     * 根据账户名模糊查找
     * @param companyBankAccountName 公司账户名
     * @return 模糊查找得到的账户
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @GetMapping(value = "/find-company-bank-account")
    public Response findByBankAccountName(@RequestParam(value = "companyBankAccountName") String companyBankAccountName)  {
        return Response.buildSuccess(companyBankAccountService.getCompanyBankAccountByName(companyBankAccountName));
    }

    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @GetMapping(value = "/find-all-account")
    public Response findAllBankAccount()  {
        return Response.buildSuccess(companyBankAccountService.getAllCompanyBankAccount());
    }
}
