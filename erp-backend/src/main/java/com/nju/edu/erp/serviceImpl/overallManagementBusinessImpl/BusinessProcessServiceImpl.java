package com.nju.edu.erp.serviceImpl.overallManagementBusinessImpl;

import com.nju.edu.erp.dao.purchaseDao.PayableSheetDao;
import com.nju.edu.erp.dao.purchaseDao.PurchaseReturnsSheetDao;
import com.nju.edu.erp.dao.purchaseDao.PurchaseSheetDao;
import com.nju.edu.erp.dao.saleDao.ReceivableSheetDao;
import com.nju.edu.erp.dao.saleDao.SaleReturnsSheetDao;
import com.nju.edu.erp.dao.saleDao.SaleSheetDao;
import com.nju.edu.erp.dao.staffDao.SalarySheetDao;
import com.nju.edu.erp.model.exception.MyServiceException;
import com.nju.edu.erp.model.po.purchase.*;
import com.nju.edu.erp.model.po.sale.*;
import com.nju.edu.erp.model.po.staff.SalarySheetPO;
import com.nju.edu.erp.model.vo.purchase.PayableSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PayableSheetVO;
import com.nju.edu.erp.model.vo.sale.ReceivableSheetContentVO;
import com.nju.edu.erp.model.vo.sale.ReceivableSheetVO;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.sale.SaleReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.sale.SaleReturnsSheetVO;
import com.nju.edu.erp.service.overallManagementBusiness.BusinessProcessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusinessProcessServiceImpl implements BusinessProcessService {

    private final SaleSheetDao saleSheetDao;

    private final SaleReturnsSheetDao saleReturnsSheetDao;

    private final PurchaseSheetDao purchaseSheetDao;

    private final PurchaseReturnsSheetDao purchaseReturnsSheetDao;

    private final ReceivableSheetDao receivableSheetDao;

    private final PayableSheetDao payableSheetDao;

    private final SalarySheetDao salarySheetDao;

    @Autowired
    public BusinessProcessServiceImpl(SaleSheetDao saleSheetDao, SaleReturnsSheetDao saleReturnsSheetDao, PurchaseSheetDao purchaseSheetDao,
                                      PurchaseReturnsSheetDao purchaseReturnsSheetDao, ReceivableSheetDao receivableSheetDao,
                                      PayableSheetDao payableSheetDao, SalarySheetDao salarySheetDao) {
        this.saleSheetDao = saleSheetDao;
        this.saleReturnsSheetDao = saleReturnsSheetDao;
        this.purchaseSheetDao = purchaseSheetDao;
        this.purchaseReturnsSheetDao = purchaseReturnsSheetDao;
        this.receivableSheetDao = receivableSheetDao;
        this.payableSheetDao = payableSheetDao;
        this.salarySheetDao = salarySheetDao;
    }

    public List<SaleSheetVO> findSaleSheet(String startTime, String endTime, Integer supplier, String salesman){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(startTime+" "+"00:00:00");
            endDate = ft.parse(endTime+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List<SaleSheetVO> res = new ArrayList<>();
        List<SaleSheetPO> sheetPOs = saleSheetDao.findSaleSheet(beginDate, endDate, supplier, salesman);
        for (SaleSheetPO po: sheetPOs){
            SaleSheetVO vo = new SaleSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleSheetContentPO> contentPOS = saleSheetDao.findContentBySheetId(po.getId());
            List<SaleSheetContentVO> contentVOS = new ArrayList<>();
            for (SaleSheetContentPO s : contentPOS) {
                SaleSheetContentVO v = new SaleSheetContentVO();
                BeanUtils.copyProperties(s, v);
                contentVOS.add(v);
            }
            vo.setSaleSheetContent(contentVOS);
            res.add(vo);
        }
        return res;
    }

    public List<SaleReturnsSheetVO> findSaleReturnsSheet(String startTime, String endTime, Integer supplier, String salesman){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(startTime+" "+"00:00:00");
            endDate = ft.parse(endTime+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List<SaleReturnsSheetVO> res = new ArrayList<>();
        List<SaleReturnsSheetPO> sheetPOs = saleReturnsSheetDao.findSaleReturnsSheet(beginDate, endDate, supplier, salesman);
        for (SaleReturnsSheetPO po: sheetPOs){
            SaleReturnsSheetVO vo = new SaleReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleReturnsSheetContentPO> contentPOS = saleReturnsSheetDao.findContentBySaleReturnsSheetId(po.getId());
            List<SaleReturnsSheetContentVO> contentVOS = new ArrayList<>();
            for (SaleReturnsSheetContentPO s : contentPOS) {
                SaleReturnsSheetContentVO v = new SaleReturnsSheetContentVO();
                BeanUtils.copyProperties(s, v);
                contentVOS.add(v);
            }
            vo.setSaleReturnsSheetContent(contentVOS);
            res.add(vo);
        }
        return res;
    }

    public List<PurchaseSheetVO> findPurchaseSheet(String startTime, String endTime, Integer supplier, String salesman){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(startTime+" "+"00:00:00");
            endDate = ft.parse(endTime+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List<PurchaseSheetVO> res = new ArrayList<>();
        List<PurchaseSheetPO> sheetPOs = purchaseSheetDao.findPurchaseSheet(beginDate, endDate, supplier, salesman);
        for (PurchaseSheetPO po: sheetPOs){
            PurchaseSheetVO vo = new PurchaseSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<PurchaseSheetContentPO> contentPOS = purchaseSheetDao.findContentByPurchaseSheetId(po.getId());
            List<PurchaseSheetContentVO> contentVOS = new ArrayList<>();
            for (PurchaseSheetContentPO s : contentPOS) {
                PurchaseSheetContentVO v = new PurchaseSheetContentVO();
                BeanUtils.copyProperties(s, v);
                contentVOS.add(v);
            }
            vo.setPurchaseSheetContent(contentVOS);
            res.add(vo);
        }
        return res;
    }

    public List<PurchaseReturnsSheetVO> findPurchaseReturnsSheet(String startTime, String endTime, Integer supplier, String salesman){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(startTime+" "+"00:00:00");
            endDate = ft.parse(endTime+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List<PurchaseReturnsSheetVO> res = new ArrayList<>();
        List<PurchaseReturnsSheetPO> sheetPOs = purchaseReturnsSheetDao.findPurchaseReturnsSheet(beginDate, endDate, supplier, salesman);
        for (PurchaseReturnsSheetPO po: sheetPOs){
            PurchaseReturnsSheetVO vo = new PurchaseReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<PurchaseReturnsSheetContentPO> contentPOS = purchaseReturnsSheetDao.findContentByPurchaseReturnsSheetId(po.getId());
            List<PurchaseReturnsSheetContentVO> contentVOS = new ArrayList<>();
            for (PurchaseReturnsSheetContentPO s : contentPOS) {
                PurchaseReturnsSheetContentVO v = new PurchaseReturnsSheetContentVO();
                BeanUtils.copyProperties(s, v);
                contentVOS.add(v);
            }
            vo.setPurchaseReturnsSheetContent(contentVOS);
            res.add(vo);
        }
        return res;
    }

    public List<ReceivableSheetVO> findReceivableSheet(String startTime, String endTime, Integer supplier, String salesman){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(startTime+" "+"00:00:00");
            endDate = ft.parse(endTime+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List<ReceivableSheetVO> res = new ArrayList<>();
        List<ReceivableSheetPO> sheetPOs = receivableSheetDao.findReceivableSheet(beginDate, endDate, supplier, salesman);
        for (ReceivableSheetPO po: sheetPOs){
            ReceivableSheetVO vo = new ReceivableSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<ReceivableSheetContentPO> pos = receivableSheetDao.findContentBySheetId(po.getId());
            List<ReceivableSheetContentVO> vos = new ArrayList<>();
            for (ReceivableSheetContentPO s : pos) {
                ReceivableSheetContentVO v = new ReceivableSheetContentVO();
                BeanUtils.copyProperties(s, v);
                vos.add(v);
            }
            vo.setReceivableContent(vos);
            res.add(vo);
        }
        return res;
    }

    public List<PayableSheetVO> findPayableSheet(String startTime, String endTime, Integer supplier, String salesman){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(startTime+" "+"00:00:00");
            endDate = ft.parse(endTime+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List<PayableSheetVO> res = new ArrayList<>();
        List<PayableSheetPO> sheetPOs = payableSheetDao.findPayableSheet(beginDate, endDate, supplier, salesman);
        for (PayableSheetPO po: sheetPOs){
            PayableSheetVO vo = new PayableSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<PayableSheetContentPO> pos = payableSheetDao.findContentBySheetId(po.getId());
            List<PayableSheetContentVO> vos = new ArrayList<>();
            for (PayableSheetContentPO s : pos) {
                PayableSheetContentVO v = new PayableSheetContentVO();
                BeanUtils.copyProperties(s, v);
                vos.add(v);
            }
            vo.setPayableContent(vos);
            res.add(vo);
        }
        return res;
    }

    public List<SalarySheetVO> findSalarySheet(String startTime, String endTime, Integer supplier, String salesman){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(startTime+" "+"00:00:00");
            endDate = ft.parse(endTime+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List<SalarySheetVO> res = new ArrayList<>();
        List<SalarySheetPO> sheetPOs = salarySheetDao.findSalarySheet(beginDate, endDate, String.valueOf(supplier), salesman);
        for (SalarySheetPO po: sheetPOs){
            SalarySheetVO vo = new SalarySheetVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }
}
