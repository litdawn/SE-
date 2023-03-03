package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.staff.SalarySheetVO;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl.GradeTaxCalculation;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


public class TaxServiceTest {

    GradeTaxCalculation gradeTaxCalculation = new GradeTaxCalculation();

    @Test
    public void testTaxCalculation1() {
        SalarySheetVO ssVO = new SalarySheetVO();
        ssVO.setBasicAmount(BigDecimal.valueOf(1000));
        Assert.assertEquals(0, BigDecimal.valueOf(360).compareTo(gradeTaxCalculation.calculateTax(ssVO)));
    }

    @Test
    public void testTaxCalculation2() {
        SalarySheetVO ssVO = new SalarySheetVO();
        ssVO.setBasicAmount(BigDecimal.valueOf(4000));
        Assert.assertEquals(0, BigDecimal.valueOf(2280).compareTo(gradeTaxCalculation.calculateTax(ssVO)));
    }

    @Test
    public void testTaxCalculation3() {
        SalarySheetVO ssVO = new SalarySheetVO();
        ssVO.setBasicAmount(BigDecimal.valueOf(13000));
        Assert.assertEquals(0, BigDecimal.valueOf(14280).compareTo(gradeTaxCalculation.calculateTax(ssVO)));
    }

    @Test
    public void testTaxCalculation4() {
        SalarySheetVO ssVO = new SalarySheetVO();
        ssVO.setBasicAmount(BigDecimal.valueOf(26000));
        Assert.assertEquals(0, BigDecimal.valueOf(46080).compareTo(gradeTaxCalculation.calculateTax(ssVO)));
    }
    @Test
    public void testTaxCalculation5() {
        SalarySheetVO ssVO = new SalarySheetVO();
        ssVO.setBasicAmount(BigDecimal.valueOf(36000));
        Assert.assertEquals(0,BigDecimal.valueOf(76680).compareTo(gradeTaxCalculation.calculateTax(ssVO)));
    }

    @Test
    public void testTaxCalculation6() {
        SalarySheetVO ssVO = new SalarySheetVO();
        ssVO.setBasicAmount(BigDecimal.valueOf(56000));
        Assert.assertEquals(0,BigDecimal.valueOf(149280).compareTo(gradeTaxCalculation.calculateTax(ssVO)));
    }

    @Test
    public void testTaxCalculation7() {
        SalarySheetVO ssVO = new SalarySheetVO();
        ssVO.setBasicAmount(BigDecimal.valueOf(90000));
        Assert.assertEquals(0,BigDecimal.valueOf(304080).compareTo(gradeTaxCalculation.calculateTax(ssVO)));
    }
}
