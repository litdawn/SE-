package com.nju.edu.erp.dao.staffDao;

import com.nju.edu.erp.model.po.staff.StaffInformationPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StaffAccountDao {
    /**
     * 存入一条员工信息记录
     * @param toSave 一条工资单记录
     * @return 影响的行数
     */
    int saveAccount(StaffInformationPO toSave);

    /**
     * 根据id寻找员工
     * @param staffId
     * @return
     */
    StaffInformationPO findById(String staffId);

    /**
     * 更新员工信息
     * @param toChange
     * @return
     */
    int changeInfo(StaffInformationPO toChange);

    List<StaffInformationPO> findAll();
}
