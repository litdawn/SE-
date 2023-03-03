package com.nju.edu.erp.web.controller.staffManagementController;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.service.staffBusiness.DailyAttendanceService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/attendance")
public class DailyAttendanceController {

    DailyAttendanceService dailyAttendanceService;

    @Autowired
    public DailyAttendanceController(DailyAttendanceService dailyAttendanceService) {
        this.dailyAttendanceService = dailyAttendanceService;
    }

    /**
     * 查询某日员工是否已经打卡
     * @param date 日期形如 yyyy-mm-dd (2022-07-03)
     * @param staffId 员工id
     * @return true:已打卡/false
     */
    @Authorized(roles = {Role.INVENTORY_MANAGER,Role.FINANCIAL_STAFF,Role.HR,Role.SALE_MANAGER,Role.SALE_STAFF})
    @GetMapping (value = "/thisDayAttendance")
    public Response doesThisDayHeAttend(@RequestParam("date")String date,
                                        @RequestParam("staffId")String staffId)  {
        boolean attend= dailyAttendanceService.doesThisDayHeAttend(staffId,date);
        return Response.buildSuccess(attend);
    }

    /**
     * 查询某月所有员工的打卡记录
     * @param yearAndMonth 年&月，形如 yyyy-mm (2022-06)
     * @return list<AttendanceVO>: staffId,name,role,date
     */
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.HR})
    @GetMapping(value = "/findAll")
    public Response findAllAttendanceRecord(@RequestParam("yearAndMonth")String yearAndMonth){
        return Response.buildSuccess(dailyAttendanceService.findALLStaffAttendance(yearAndMonth));
    }

    /**
     * 查询某月某位员工的打卡记录
     * @param yearAndMonth 年&月，形如 yyyy-mm (2022-06)
     * @param staffId  员工id
     * @return list<AttendanceVO>: staffId,name,role,date
     */
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.HR})
    @GetMapping(value = "/findSomebody")
    public Response findSomebodyAttendanceRecord(@RequestParam("yearAndMonth")String yearAndMonth,
                                                 @RequestParam("staffId")String staffId){
        return Response.buildSuccess(dailyAttendanceService.findOneStaffAttendance(yearAndMonth,staffId));
    }

    /**
     * 调用此函数进行每日打卡
     * @param staffId 员工id
     * @param name 员工姓名
     * @param role 员工岗位
     */
    @GetMapping(value = "/register")
    public Response attendanceRecordRegister(@RequestParam("staffId")String staffId,
                                             @RequestParam("name")String name,
                                             @RequestParam("role")String role){
        dailyAttendanceService.attendanceRegister(staffId,name,role);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.ADMIN,Role.GM,Role.HR,Role.SALE_STAFF,Role.INVENTORY_MANAGER,Role.SALE_MANAGER,Role.FINANCIAL_STAFF})
    @GetMapping(value = "/findStaffIdByName")
    public Response findStaffIdByName(@RequestParam ("name")String name){
        return Response.buildSuccess(dailyAttendanceService.findIdByName(name));
    }


}
