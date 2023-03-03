package com.nju.edu.erp.model.vo.staff;

import com.nju.edu.erp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceVO {

    /*
    员工id
    */
    String staffId;

    /*
    员工姓名
     */
    String name;

    /*
    员工职位
     */
    Role role;

    /*
    打卡日期
     */
    Date date;
}
