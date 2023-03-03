package com.nju.edu.erp.service;


import com.nju.edu.erp.dao.staffDao.StaffAccountDao;
import com.nju.edu.erp.model.po.staff.StaffInformationPO;
import com.nju.edu.erp.model.vo.staff.StaffInformationVO;
import com.nju.edu.erp.service.staffBusiness.StaffAccountService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.nju.edu.erp.enums.Role.SALE_STAFF;
import static com.nju.edu.erp.enums.SalaryRule.COMMISSIONED;

@SpringBootTest
public class StaffAccountServiceTest {
    @Autowired
    StaffAccountDao staffAccountDao;

    @Autowired
    StaffAccountService staffAccountService;

    @Test
    @Transactional
    public void creatStaffAccount(){
        StaffInformationVO staffInformationVO = StaffInformationVO.builder()
                .staffId("4399")
                .name("游小戏")
                .gender('m')
                .birthDay("20000101")
                .phoneNumber("12315")
                .role(SALE_STAFF)
                .basicAmount(BigDecimal.valueOf(6000))
                .positionAmount(BigDecimal.valueOf(6000))
                .level(1)
                .paymentCalculation("分级扣税")
                .paymentSchedule(COMMISSIONED)
                .staffBankAccount("10086")
                .build();

        staffAccountService.creatStaffAccount(staffInformationVO);

        StaffInformationPO result = staffAccountService.findByStaffId("4399");
        Assertions.assertEquals(result.getName(), staffInformationVO.getName());
    }

    @Test
    @Transactional
    public void changeStaffInfo(){
        StaffInformationVO staffInformationVO = StaffInformationVO.builder()
                .staffId("4399")
                .name("游小戏")
                .gender('m')
                .birthDay("20000101")
                .phoneNumber("12315")
                .role(SALE_STAFF)
                .basicAmount(BigDecimal.valueOf(6000))
                .positionAmount(BigDecimal.valueOf(6000))
                .level(1)
                .paymentCalculation("分级扣税")
                .paymentSchedule(COMMISSIONED)
                .staffBankAccount("10086")
                .build();
        staffAccountService.creatStaffAccount(staffInformationVO);
        StaffInformationVO updateStaffInformationVO = StaffInformationVO.builder()
                .staffId("4399")
                .name("游小戏")
                .gender('m')
                .birthDay("20000101")
                .phoneNumber("12315")
                .role(SALE_STAFF)
                .basicAmount(BigDecimal.valueOf(7000))
                .positionAmount(BigDecimal.valueOf(6000))
                .level(1)
                .paymentCalculation("分级扣税")
                .paymentSchedule(COMMISSIONED)
                .staffBankAccount("10086")
                .build();
        staffAccountService.changeStaffInfo(updateStaffInformationVO);

        StaffInformationPO result = staffAccountService.findByStaffId("4399");

        BigDecimal value = BigDecimal.valueOf(7000);
        Assert.assertEquals(value.setScale(2), result.getBasicAmount());
    }
}
