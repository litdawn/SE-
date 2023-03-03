package com.nju.edu.erp.model.vo.staff;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.SalaryRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffInformationVO {
    /**
     * 员工ID
     */
    private String staffId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private char gender;

    /**
     * 出生日期
     */
    private String birthDay;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 工作岗位
     */
    private Role role;

    /**
     * 基本工资
     */
    private BigDecimal basicAmount;

    /**
     * 岗位工资
     */
    private BigDecimal positionAmount;

    /**
     * 岗位级别
     */
    private int level;

    /**
     * 薪资计算方式(分级扣税
     */
    private String paymentCalculation;

    /**
     * 薪资发放方式
     */
    private SalaryRule paymentSchedule;

    /**
     * 工资卡账户
     */
    private String staffBankAccount;
}
