package com.nju.edu.erp.serviceImpl.overallManagementBusinessImpl;



import com.nju.edu.erp.dao.warehouseDao.ProductDao;
import com.nju.edu.erp.dao.saleDao.SaleSheetDao;
import com.nju.edu.erp.model.exception.MyServiceException;
import com.nju.edu.erp.model.po.overallManagement.SaleStatusSheetPO;
import com.nju.edu.erp.model.vo.overallManagement.SaleStatusSheetVO;
import com.nju.edu.erp.service.overallManagementBusiness.SaleStatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaleStatusServiceImpl implements SaleStatusService {

    private final SaleSheetDao saleSheetDao;

    private final ProductDao productDao;

    @Autowired
    public SaleStatusServiceImpl(SaleSheetDao saleSheetDao, ProductDao productDao) {
        this.saleSheetDao = saleSheetDao;
        this.productDao = productDao;
    }

    public List<SaleStatusSheetVO> getSaleStatusSheet(String startTime, String endTime, String name, Integer supplier, String salesman){
        Date beginDate;
        Date endDate;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            beginDate= ft.parse(startTime+" "+"00:00:00");
            endDate = ft.parse(endTime+" "+"23:59:59");
        }catch (ParseException p){
            throw new MyServiceException("D0002","时间格式有误");
        }
        List<SaleStatusSheetVO> res = new ArrayList<>();
        String pid = productDao.findByName(name).getId();
        List<SaleStatusSheetPO> sheetPOs = saleSheetDao.findSheet(beginDate, endDate, pid, supplier, salesman);
        for (SaleStatusSheetPO po: sheetPOs){
            po.setName(name);
            po.setType(productDao.findById(pid).getType());
            SaleStatusSheetVO vo = new SaleStatusSheetVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }
}
