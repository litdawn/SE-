package com.nju.edu.erp.service.staffBusiness;

import com.nju.edu.erp.enums.SalaryRule;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.staff.SalarySheetPO;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;

import java.math.BigDecimal;
import java.util.List;

// 制定工资单 + 总经理二级审批 + 制订薪酬规则
public interface SalaryService {
    /**
     * 制定工资单
     * @param salarySheetVO 工资单
     */
    void makeSalarySheet(UserVO userVO, SalarySheetVO salarySheetVO);

    /**
     * 根据状态获取工资单(state == null 则获取所有工资单)
     * @param state 工资单状态
     * @return 工资单
     */
    List<SalarySheetVO> getSalarySheetByState(SalarySheetState state);

    /**
     * 根据工资单id进行审批(state == "审批完成"/"审批失败")
     * 在controller层进行权限控制
     * @param salarySheetId 工资单id
     * @param state 工资单修改后的状态
     */
    void approval(String salarySheetId, SalarySheetState state);

    /**
     * 根据工资单Id搜索进货单信息
     * @param salarySheetId 工资单Id
     * @return 工资单全部信息
     */
    SalarySheetVO getSalarySheetById(String salarySheetId);

    /**
     * 改变id为staffId员工的薪酬规则为rule
     * @param staffId 员工Id
     * @param rule 改变后的规则，SalaryRule枚举类
     */
    void makeSalaryRule(String staffId, SalaryRule rule);

    /**
     * 前11月的工资
     * @param staffId 员工id
     * @return
     */
    BigDecimal checkPrevSalary(String staffId);

    /**
     * 查找所有工资单
     * @return
     */
    List<SalarySheetPO> findAllSheet();

}
