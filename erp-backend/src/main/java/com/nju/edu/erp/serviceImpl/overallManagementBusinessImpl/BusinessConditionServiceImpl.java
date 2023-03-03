package com.nju.edu.erp.serviceImpl.overallManagementBusinessImpl;


import com.nju.edu.erp.dao.purchaseDao.PurchaseSheetDao;
import com.nju.edu.erp.dao.staffDao.SalarySheetDao;
import com.nju.edu.erp.dao.saleDao.SaleSheetDao;
import com.nju.edu.erp.model.exception.MyServiceException;
import com.nju.edu.erp.model.po.purchase.PurchaseSheetPO;
import com.nju.edu.erp.model.po.staff.SalarySheetPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import com.nju.edu.erp.model.vo.overallManagement.BusinessConditionSheetVO;
import com.nju.edu.erp.service.overallManagementBusiness.BusinessConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BusinessConditionServiceImpl implements BusinessConditionService {

    private final SaleSheetDao saleSheetDao;

    private final PurchaseSheetDao purchaseSheetDao;

    private final SalarySheetDao salarySheetDao;

    @Autowired
    public BusinessConditionServiceImpl(SaleSheetDao saleSheetDao, PurchaseSheetDao purchaseSheetDao, SalarySheetDao salarySheetDao) {
        this.saleSheetDao = saleSheetDao;
        this.purchaseSheetDao = purchaseSheetDao;
        this.salarySheetDao = salarySheetDao;
    }

    public BusinessConditionSheetVO getBusinessConditionSheet(String startTime, String endTime){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(startTime+" "+"00:00:00");
            endDate = ft.parse(endTime+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        BusinessConditionSheetVO res = new BusinessConditionSheetVO();
        BigDecimal finalAmount = BigDecimal.ZERO;
        BigDecimal discountAmount = BigDecimal.ZERO;
        List<SaleSheetPO> saleSheetPOs = saleSheetDao.findAllByTime(beginDate, endDate);
        for (SaleSheetPO po: saleSheetPOs){
            finalAmount = finalAmount.add(po.getFinalAmount());
            discountAmount = discountAmount.add(po.getRawTotalAmount().subtract(po.getFinalAmount()));
        }
        res.setFinalAmount(finalAmount);
        res.setDiscountAmount(discountAmount);
        BigDecimal expenditureAmount = BigDecimal.ZERO;
        List<SalarySheetPO> salarySheetPOs = salarySheetDao.findAllByTime(beginDate, endDate);
        for (SalarySheetPO po: salarySheetPOs){
            expenditureAmount = expenditureAmount.add(po.getFinalAmount());
        }
        List<PurchaseSheetPO> purchaseSheetPOs = purchaseSheetDao.findAllByTime(beginDate, endDate);
        for (PurchaseSheetPO po: purchaseSheetPOs){
            expenditureAmount = expenditureAmount.add(po.getTotalAmount());
        }
        res.setExpenditureAmount(expenditureAmount);
        BigDecimal profit = BigDecimal.ZERO;
        profit = finalAmount.subtract(expenditureAmount);
        res.setProfit(profit);
        return res;
    }
}
