package com.nju.edu.erp.dao.overallManagementDao;

import com.nju.edu.erp.model.po.overallManagement.CompanyBankAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface CompanyBankAccountDao {
    /**
     * 存入新增加的公司账户
     * @param toSave
     * @return 影响的行数
     */
    int saveAccount(CompanyBankAccountPO toSave);
    /**
     * 删除公司账户
     * @param companyBankAccountName
     * @return 影响的行数
     */
    int deleteAccount(String companyBankAccountName);
    /**
     * 修改公司账户属性（名称）
     * @param id, companyBankAccountName
     */
    int changeAccountName(String id, String companyBankAccountName);
    /**
     * 减少公司余额
     * @param id
     */
    int reduceAccountMoney(String id, BigDecimal amount);
    /**
     * 根据账户名模糊查找
     * @param companyBankAccountName 公司账户名
     * @return 模糊查找得到的账户
     */
    List<CompanyBankAccountPO> findAllByName(String companyBankAccountName);

    List<CompanyBankAccountPO> findAll();


    /**
     * 查看该账户余额（测试用
     * @param id
     */
    CompanyBankAccountPO getAmount(String id);
}
