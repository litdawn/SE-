package com.nju.edu.erp.serviceImpl.staffBusinessImpl;


import com.nju.edu.erp.dao.staffDao.StaffAccountDao;
import com.nju.edu.erp.model.exception.MyServiceException;
import com.nju.edu.erp.model.po.staff.StaffInformationPO;
import com.nju.edu.erp.model.vo.staff.StaffInformationVO;
import com.nju.edu.erp.service.staffBusiness.StaffAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffAccountServiceImpl implements StaffAccountService {

    StaffAccountDao staffAccountDao;

    @Autowired
    public StaffAccountServiceImpl(StaffAccountDao staffAccountDao) {
        this.staffAccountDao = staffAccountDao;
    }

    /**
     * 创建员工账户
     *
     * @param staffInformationVO 员工信息
     */
    @Override
    @Transactional
    public void creatStaffAccount(StaffInformationVO staffInformationVO) {
        StaffInformationPO staffInformationPO = new StaffInformationPO();
        BeanUtils.copyProperties(staffInformationVO, staffInformationPO);
        if(staffInformationPO.getGender()!='m'&&staffInformationPO.getGender()!='f')
            throw new MyServiceException("D0003","性别格式有误");
        staffAccountDao.saveAccount(staffInformationPO);
    }

    /**
     * 修改员工账户信息
     *
     * @param staffInformationVO 员工信息
     */
    @Override
    @Transactional
    public void changeStaffInfo(StaffInformationVO staffInformationVO) {
        StaffInformationPO staffInformationPO = new StaffInformationPO();
        BeanUtils.copyProperties(staffInformationVO, staffInformationPO);
        staffAccountDao.changeInfo(staffInformationPO);
    }

    @Override
    public StaffInformationPO findByStaffId(String staffId){
        return staffAccountDao.findById(staffId);
    }

    public List<StaffInformationVO> showAllStaffAccount(){
        List<StaffInformationVO> res = new ArrayList<>();
        List<StaffInformationPO> all;
        all = staffAccountDao.findAll();
        for(StaffInformationPO po: all) {
            StaffInformationVO vo = new StaffInformationVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }

    public List<String> getAllStaffId(){
        List<StaffInformationPO> staffList = staffAccountDao.findAll();
        List<String> res = new ArrayList<>();
        for (StaffInformationPO po: staffList){
            res.add(po.getStaffId());
        }
        return res;
    }

}
