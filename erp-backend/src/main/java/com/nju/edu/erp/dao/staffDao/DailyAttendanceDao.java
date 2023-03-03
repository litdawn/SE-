package com.nju.edu.erp.dao.staffDao;

import com.nju.edu.erp.model.po.staff.AttendancePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface DailyAttendanceDao {
    /**
     *
     * @param staffId 员工id
     * @param beginDate 开始日期（某天）
     * @param endDate 结束日期（某天）
     * @return
     */
    AttendancePO doesThisDayHeAttend(String staffId, Date beginDate, Date endDate);

    /**
     *
     * @param beginDate 开始日期（某月）
     * @param endDate 结束日期（某月）
     * @return
     */
    List<AttendancePO> findAll(Date beginDate,Date endDate);

    /**
     *查询：
     * @param beginDate  开始日期（某月）
     * @param endDate 结束日期（某月）
     * @param staffId 员工id
     * @return
     */
    List<AttendancePO> findOne(Date beginDate,Date endDate,String staffId);

    /**
     * 签到用
     * @param attendancePO
     */
    void AttendanceRegister(AttendancePO attendancePO);

    String findIdByName(String name);
}
