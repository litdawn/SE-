package com.nju.edu.erp.serviceImpl.staffBusinessImpl;

import com.nju.edu.erp.dao.staffDao.DailyAttendanceDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.exception.MyServiceException;
import com.nju.edu.erp.model.po.staff.AttendancePO;
import com.nju.edu.erp.model.vo.staff.AttendanceVO;
import com.nju.edu.erp.service.staffBusiness.DailyAttendanceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DailyAttendanceServiceImpl implements DailyAttendanceService {

    private final DailyAttendanceDao dailyAttendanceDao;


    @Autowired
    public  DailyAttendanceServiceImpl(DailyAttendanceDao dailyAttendanceDao){
        this.dailyAttendanceDao = dailyAttendanceDao;
    }

    @Override
    @Transactional
    public boolean doesThisDayHeAttend(String staffId, String date){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(date+" "+"00:00:00");
            endDate = ft.parse(date+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        return dailyAttendanceDao.doesThisDayHeAttend(staffId, beginDate, endDate) != null;
    }

    @Override
    @Transactional
    public List<AttendanceVO> findALLStaffAttendance(String date){
        Date beginDate;
        Date endDate;
        try {
            int year = Integer.parseInt(date.split("-")[0]);
            int month = Integer.parseInt(date.split("-")[1]);
            Calendar c= Calendar.getInstance();
            c.set(year,month,0);
            String lastDay = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(date+"-"+"01"+" "+"00:00:00");
            endDate = ft.parse(date+"-"+lastDay+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List <AttendancePO> attendancePO = dailyAttendanceDao.findAll(beginDate, endDate);
        return changePOtoVO(attendancePO);

    }

    @Override
    @Transactional
    public List<AttendanceVO> findOneStaffAttendance(String date,String staffId){
        Date beginDate;
        Date endDate;
        try {
            int year = Integer.parseInt(date.split("-")[0]);
            int month = Integer.parseInt(date.split("-")[1]);
            Calendar c= Calendar.getInstance();
            c.set(year,month,0);
            String lastDay = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(date+"-"+"01"+" "+"00:00:00");
            endDate = ft.parse(date+"-"+lastDay+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List <AttendancePO> attendancePO = dailyAttendanceDao.findOne(beginDate, endDate,staffId);
        return changePOtoVO(attendancePO);
    }

    List <AttendanceVO> changePOtoVO(List <AttendancePO> attendancePO){
        List <AttendanceVO> attendanceVO = new ArrayList<>();
        for(AttendancePO po:attendancePO){
            AttendanceVO vo = new AttendanceVO();
            BeanUtils.copyProperties(po, vo);
            attendanceVO.add(vo);
        }
        return attendanceVO;
    }

    public void attendanceRegister(String staffId,String name,String role){
        AttendancePO po = new AttendancePO(staffId,name, Role.valueOf(role),new Date());
        dailyAttendanceDao.AttendanceRegister(po);
    }

    public String findIdByName(String name){
        return dailyAttendanceDao.findIdByName(name);
    }
}
