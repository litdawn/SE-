package com.nju.edu.erp.service.staffBusiness;

import com.nju.edu.erp.model.vo.staff.AttendanceVO;

import java.util.List;

public interface DailyAttendanceService {
    /**
     *  查询某人是否某日已打卡
     * @param staffId 员工id
     * @param date
     */
    boolean doesThisDayHeAttend(String staffId, String date);

    /**
     * 查询所有员工某年某月打卡记录
     * @param date
     */
    List<AttendanceVO> findALLStaffAttendance(String date);

    /**
     *  查询某位员工某年某月打卡记录
     * @param date
     * @param staffId 员工id
     */
    List<AttendanceVO> findOneStaffAttendance(String date,String staffId);

    /**
     *  打卡
     * @param staffId 员工id
     */
    void attendanceRegister(String staffId,String name,String role);

    String findIdByName(String name);
}
