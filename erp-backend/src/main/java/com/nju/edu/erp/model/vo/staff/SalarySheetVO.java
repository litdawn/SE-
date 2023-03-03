package com.nju.edu.erp.model.vo.staff;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.SalaryRule;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalarySheetVO {
    /**
     * 工资单编号（格式为：GZD-yyyyMMdd-xxxxx), 新建单据时前端传null
     */
    private String id;
    /**
     * 员工id
     */
    private String staffId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 工作岗位
     */
    private Role role;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 银行账户
     */
    private String staffBankAccount;
    /**
     * 基本工资
     */
    private BigDecimal basicAmount;
    /**
     * 岗位工资
     */
    private BigDecimal positionAmount;
    /**
     * 应发工资, 新建单据时前端传null(在后端计算
     */
    private BigDecimal rawAmount;
    /**
     * 税款
     */
    private BigDecimal taxAmount;
    /**
     * 提成
     */
    private double pushMoney;
    /**
     * 缺勤次数
     */
    private int absenceTimes;
    /**
     * 年终奖
     */
    private BigDecimal annualBonus;
    /**
     * 实发金额, 新建单据时前端传null(在后端计算实发金额
     */
    private BigDecimal finalAmount;
    /**
     * 薪资发放方式
     */
    private SalaryRule paymentSchedule;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private SaleSheetState state;
}
