package com.nju.edu.erp.service.overallManagementBusiness;

import com.nju.edu.erp.model.vo.overallManagement.CompanyBankAccountVO;

import java.util.List;
public interface CompanyBankAccountService {
    /**
     * 新建公司银行账户
     * @param companyBankAccountVO 公司银行账户
     */
    void createCompanyBankAccount(CompanyBankAccountVO companyBankAccountVO);
    /**
     * 删除公司银行账户
     * @param id 公司银行账户id
     */
    void deleteCompanyBankAccount(String id);
    /**
     * 修改账户属性(只能修改账户名)
     * @param companyBankAccountVO 公司银行账户
     */
    void changeCompanyBankAccountName(CompanyBankAccountVO companyBankAccountVO);
    /**
     * 根据账户名模糊查找
     * @param companyBankAccountName 公司账户名
     * @return 模糊查找得到的账户
     */
    List<CompanyBankAccountVO> getCompanyBankAccountByName(String companyBankAccountName);

    List<CompanyBankAccountVO> getAllCompanyBankAccount();
}
