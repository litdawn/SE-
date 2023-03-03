package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.customerDao.CustomerDao;
import com.nju.edu.erp.dao.overallManagementDao.CompanyBankAccountDao;
import com.nju.edu.erp.dao.purchaseDao.PayableSheetDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.model.po.overallManagement.CompanyBankAccountPO;
import com.nju.edu.erp.model.po.purchase.PayableSheetPO;
import com.nju.edu.erp.model.vo.purchase.PayableSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PayableSheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.service.customerBusiness.CustomerService;
import com.nju.edu.erp.service.purchaseBusiness.PayableService;
import com.nju.edu.erp.utils.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



@SpringBootTest
public class PayableServiceTest {    //测试前请execute数据库, customerId为2的payable为100000.00,无id为6666的银行账户

    @Autowired
    PayableSheetDao payableSheetDao;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    PayableService payableService;

    @Autowired
    CompanyBankAccountDao companyBankAccountDao;

    @Test
//    @Transactional
//    @Rollback(value = true)
    public void makePayableSheet() {
        UserVO userVO = UserVO.builder()
                .name("xiaoshoujingli")
                .role(Role.FINANCIAL_STAFF)
                .build();

        CompanyBankAccountPO companyBankAccountPO = CompanyBankAccountPO.builder()
                .id("6666")
                .companyBankAccountName("第一个账户")
                .companyBankAccountMoney(BigDecimal.valueOf(100000))
                .build();
        companyBankAccountDao.saveAccount(companyBankAccountPO);

        List<PayableSheetContentVO> payableSheetContentVOS = new ArrayList<>();
        payableSheetContentVOS.add(PayableSheetContentVO.builder()
                .name("1")
                .amount(BigDecimal.valueOf(10000))
                .remark("这才一万")
                .build());
        payableSheetContentVOS.add(PayableSheetContentVO.builder()
                .name("2")
                .amount(BigDecimal.valueOf(20000))
                .remark("加倍")
                .build());
        payableSheetContentVOS.add(PayableSheetContentVO.builder()
                .name("3")
                .amount(BigDecimal.valueOf(40000))
                .remark("超级加倍")
                .build());
        PayableSheetVO payableSheetVO = PayableSheetVO.builder()
                .bankAccount("6666")
                .customerId(2)
                .payableContent(payableSheetContentVOS)
                .totalAmount(BigDecimal.valueOf(70000))
                .build();

        PayableSheetPO prevSheet = payableSheetDao.getLatest();
        String realSheetId = IdGenerator.generateSheetId(prevSheet == null ? null : prevSheet.getId(), "XJFYD");

        payableService.makePayableSheet(userVO, payableSheetVO);
        PayableSheetPO latestSheet = payableSheetDao.getLatest();
        Assertions.assertNotNull(latestSheet);
        Assertions.assertEquals(realSheetId, latestSheet.getId());
        Assertions.assertEquals(PayableSheetState.PENDING_LEVEL_2, latestSheet.getState());
    }


    @Test
//    @Transactional
//    @Rollback(value = true)
    public void approval() { // 测试总经理审批
        // 二级审批成功之后需要进行
        // 1. 修改单据状态
        // 2. 修改账户余额
        // 2. 修改客户应付
        PayableSheetPO prevSheet = payableSheetDao.getLatest();
        payableService.approval(prevSheet.getId(), PayableSheetState.SUCCESS);
        PayableSheetPO sheet = payableSheetDao.findOneById(prevSheet.getId());
        Assertions.assertEquals(PayableSheetState.SUCCESS,sheet.getState());

        Assertions.assertEquals(0, companyBankAccountDao.getAmount("6666").getCompanyBankAccountMoney().compareTo(BigDecimal.valueOf(30000.00)));
        Assertions.assertEquals(0,customerDao.findOneById(2).getPayable().compareTo(BigDecimal.valueOf(170000.00)));
    }

    @Test
//    @Transactional
//    @Rollback(value = true)
    public void getAllPayableSheet() { // 获取所有付款单及其content是否成功
        List<PayableSheetVO> payableSheet = payableService.getAllPayableSheet();
        Assertions.assertNotNull(payableSheet);
        Assertions.assertEquals(1, payableSheet.size());
        PayableSheetVO sheet1 = payableSheet.get(0);
        Assertions.assertNotNull(sheet1);
        PayableSheetPO prevSheet = payableSheetDao.getLatest();
        Assertions.assertEquals(prevSheet.getId(), sheet1.getId());

        List<PayableSheetContentVO> sheet1Content = sheet1.getPayableContent();
        Assertions.assertNotNull(sheet1Content);
        Assertions.assertEquals(3, sheet1Content.size());
        sheet1Content.sort(Comparator.comparing(PayableSheetContentVO::getName));
        Assertions.assertEquals("这才一万", sheet1Content.get(0).getRemark());
        Assertions.assertEquals("加倍", sheet1Content.get(1).getRemark());
        Assertions.assertEquals("超级加倍", sheet1Content.get(2).getRemark());
    }
}
