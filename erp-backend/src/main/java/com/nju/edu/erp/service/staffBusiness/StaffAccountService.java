package com.nju.edu.erp.service.staffBusiness;


import com.nju.edu.erp.model.po.staff.StaffInformationPO;
import com.nju.edu.erp.model.vo.staff.StaffInformationVO;

import java.util.List;

public interface StaffAccountService {
    /**
     * 登记入职员工信息，系统创建账户
     * @param staffInformationVO
     */
    void creatStaffAccount(StaffInformationVO staffInformationVO);
    /**
     * 修改入职员工信息
     * @param staffInformationVO
     */
    void changeStaffInfo(StaffInformationVO staffInformationVO);
    /**
     * 根据staffId找员工
     * @param staffId
     */
    StaffInformationPO findByStaffId(String staffId);

    List<StaffInformationVO> showAllStaffAccount();

    List<String> getAllStaffId();
}
