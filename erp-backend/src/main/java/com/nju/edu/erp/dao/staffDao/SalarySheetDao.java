package com.nju.edu.erp.dao.staffDao;


import com.nju.edu.erp.enums.SalaryRule;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.staff.SalarySheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SalarySheetDao {

    /**
     * 获取最近一条工资单
     */
    SalarySheetPO getLatestSheet();

    /**
     * 存入一条工资单记录
     * @param toSave 一条工资单记录
     * @return 影响的行数
     */
    int saveSheet(SalarySheetPO toSave);

    /**
     * 查找所有工资单（根据状态查找时若为null需要用到这里
     */
    List<SalarySheetPO> findAllSheet();

    /**
     * 根据state返回工资单
     * @param state 工资单状态
     * @return 工资单列表
     */
    List<SalarySheetPO> findAllByState(SalarySheetState state);

    /**
     * 查找指定id的工资单
     * @param id id
     */
    SalarySheetPO findSheetById(String id);

    /**
     * 根据时间返回工资单
     * @param startTime,endTime
     * @return 工资单列表
     */
    List<SalarySheetPO> findAllByTime(Date startTime, Date endTime);

    /**
     * 更新指定工资单的状态
     * @param sheetId 编号
     * @param state 状态
     */
    int updateSheetState(String sheetId, SalarySheetState state);


    int makeSalaryRule(String staffId, SalaryRule rule);

    /**
     * 用与选取符合条件的(经营历程列表
     * @param startTime
     * @param endTime
     * @param staffId
     * @param salesman
     * @return
     */
    List<SalarySheetPO> findSalarySheet(Date startTime, Date endTime, String staffId, String salesman);

    /**
     * 用于查看前11个月薪资
     */
    List<SalarySheetPO> findSheetByIdTime(String staffId, String year);
}
