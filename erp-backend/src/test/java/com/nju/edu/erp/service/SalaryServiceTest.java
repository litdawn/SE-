package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.staffDao.SalarySheetDao;
import com.nju.edu.erp.enums.SalaryRule;
import com.nju.edu.erp.model.po.staff.SalarySheetPO;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.SalaryServiceImpl;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl.GradeTaxCalculation;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.TaxCalculation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class SalaryServiceTest {
    //mock注解创建一个被mock的实例
    @Mock
    SalarySheetDao salarySheetDao;


    //InjectMocks代表创建一个实例，其他带mock注解的示例将被注入到该实例用。
    //可以用该注解创建要被测试的实例，将实例所需的依赖用mock注解创建，即可mock掉依赖
    @InjectMocks
    SalaryServiceImpl SalaryServiceImpl;



    /**
     * 初始化时设置一些需要mock的方法和返回值
     */
    @Before
    public void init(){
        Mockito.when(salarySheetDao.getLatestSheet()).thenReturn(new SalarySheetPO());
    }
    //月薪
    @Test
    public void testMakeSalarySheetMonthly(){
        SalarySheetVO salarySheetVOMonthly = new SalarySheetVO();
        salarySheetVOMonthly.setPaymentSchedule(SalaryRule.MONTHLY);
        salarySheetVOMonthly.setBasicAmount(BigDecimal.valueOf(3000));
        salarySheetVOMonthly.setPositionAmount(BigDecimal.valueOf(1000));
        salarySheetVOMonthly.setAbsenceTimes(2);
        salarySheetVOMonthly.setAnnualBonus(BigDecimal.ZERO);
        SalaryServiceImpl.makeSalarySheet(new UserVO(), salarySheetVOMonthly);

        Assert.assertEquals(BigDecimal.valueOf(4000),salarySheetVOMonthly.getRawAmount());
    }

    //年薪
    @Test
    public void testMakeSalarySheetYearly(){
        SalarySheetVO salarySheetVOYearly = new SalarySheetVO();
        salarySheetVOYearly.setPaymentSchedule(SalaryRule.YEARLY);
        salarySheetVOYearly.setBasicAmount(BigDecimal.valueOf(4000));
        salarySheetVOYearly.setPositionAmount(BigDecimal.valueOf(1000));
        salarySheetVOYearly.setAbsenceTimes(2);
        salarySheetVOYearly.setAnnualBonus(BigDecimal.valueOf(1000));
        SalaryServiceImpl.makeSalarySheet(new UserVO(), salarySheetVOYearly);
        Assert.assertEquals(0,BigDecimal.valueOf(2280.00).compareTo(salarySheetVOYearly.getTaxAmount()));
        Assert.assertEquals(BigDecimal.valueOf(49000),salarySheetVOYearly.getRawAmount());
    }

    @Test
    public void testMakeSalarySheetCommission(){
        SalarySheetVO salarySheetVOCommission = new SalarySheetVO();
        salarySheetVOCommission.setPaymentSchedule(SalaryRule.COMMISSIONED);
        salarySheetVOCommission.setBasicAmount(BigDecimal.valueOf(4000));
        salarySheetVOCommission.setPositionAmount(BigDecimal.valueOf(1000));
        salarySheetVOCommission.setPushMoney(0.01);
        salarySheetVOCommission.setAbsenceTimes(2);
        salarySheetVOCommission.setAnnualBonus(BigDecimal.ZERO);
        SalaryServiceImpl.makeSalarySheet(new UserVO(), salarySheetVOCommission);
        Assert.assertEquals(BigDecimal.valueOf(5000),salarySheetVOCommission.getRawAmount());
    }

}

