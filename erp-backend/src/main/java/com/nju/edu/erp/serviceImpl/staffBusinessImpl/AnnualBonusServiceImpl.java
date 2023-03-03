package com.nju.edu.erp.serviceImpl.staffBusinessImpl;

import com.nju.edu.erp.dao.staffDao.AnnualBonusDao;
import com.nju.edu.erp.dao.staffDao.DailyAttendanceDao;
import com.nju.edu.erp.model.po.staff.AnnualBonusPO;
import com.nju.edu.erp.service.staffBusiness.AnnualBonusService;
import com.nju.edu.erp.service.staffBusiness.StaffAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {
    private final AnnualBonusDao annualBonusDao;

    private final StaffAccountService staffAccountService;

    @Autowired
    public  AnnualBonusServiceImpl(AnnualBonusDao annualBonusDao, StaffAccountService staffAccountService){
        this.annualBonusDao = annualBonusDao;
        this.staffAccountService = staffAccountService;
    }

    public void setAnnualAmount(String staffId, BigDecimal annualAmount){
        AnnualBonusPO po = new AnnualBonusPO();
        po.setStaffId(staffId);
        po.setAnnualAmount(annualAmount);
        annualBonusDao.save(po);
    }

    public BigDecimal findAnnualAmountById(String staffId){
        return annualBonusDao.getAmount(staffId);
    }
}
