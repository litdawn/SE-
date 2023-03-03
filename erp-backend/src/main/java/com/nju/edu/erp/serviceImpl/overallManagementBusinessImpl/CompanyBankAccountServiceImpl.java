package com.nju.edu.erp.serviceImpl.overallManagementBusinessImpl;

import com.nju.edu.erp.dao.overallManagementDao.CompanyBankAccountDao;
import com.nju.edu.erp.model.po.overallManagement.CompanyBankAccountPO;
import com.nju.edu.erp.model.vo.overallManagement.CompanyBankAccountVO;
import com.nju.edu.erp.service.overallManagementBusiness.CompanyBankAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyBankAccountServiceImpl implements CompanyBankAccountService {

    CompanyBankAccountDao companyBankAccountDao;

    @Autowired
    public CompanyBankAccountServiceImpl(CompanyBankAccountDao companyBankAccountDao) {
        this.companyBankAccountDao = companyBankAccountDao;
    }

    public void createCompanyBankAccount(CompanyBankAccountVO companyBankAccountVO){
        CompanyBankAccountPO companyBankAccountPO = new CompanyBankAccountPO();
        BeanUtils.copyProperties(companyBankAccountVO, companyBankAccountPO);
        companyBankAccountDao.saveAccount(companyBankAccountPO);
    }

    public void deleteCompanyBankAccount(String id){
        companyBankAccountDao.deleteAccount(id);
    }

    public void changeCompanyBankAccountName(CompanyBankAccountVO companyBankAccountVO){
        companyBankAccountDao.changeAccountName(companyBankAccountVO.getId(), companyBankAccountVO.getCompanyBankAccountName());
    }

    public List<CompanyBankAccountVO> getCompanyBankAccountByName(String companyBankAccountName){
        List<CompanyBankAccountVO> res = new ArrayList<>();
        List<CompanyBankAccountPO> all;
        all = companyBankAccountDao.findAllByName(companyBankAccountName);
        for(CompanyBankAccountPO po: all) {
            CompanyBankAccountVO vo = new CompanyBankAccountVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }

    public List<CompanyBankAccountVO> getAllCompanyBankAccount(){
        List<CompanyBankAccountVO> res = new ArrayList<>();
        List<CompanyBankAccountPO> all;
        all = companyBankAccountDao.findAll();
        for(CompanyBankAccountPO po: all) {
            CompanyBankAccountVO vo = new CompanyBankAccountVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }
}
