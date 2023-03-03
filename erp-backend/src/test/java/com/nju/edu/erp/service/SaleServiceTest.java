package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.customerDao.CustomerDao;
import com.nju.edu.erp.dao.saleDao.GiftSheetDao;
import com.nju.edu.erp.dao.saleDao.PromotionDao;
import com.nju.edu.erp.dao.warehouseDao.ProductDao;
import com.nju.edu.erp.dao.saleDao.SaleSheetDao;
import com.nju.edu.erp.dao.warehouseDao.WarehouseOutputSheetDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.enums.sheetState.WarehouseOutputSheetState;
import com.nju.edu.erp.model.po.sale.GiftSheetPO;
import com.nju.edu.erp.model.po.sale.PromotionPO;
import com.nju.edu.erp.model.po.sale.SaleSheetContentPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import com.nju.edu.erp.model.po.warehouse.WarehouseOutputSheetPO;
import com.nju.edu.erp.model.vo.sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;

import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.service.saleBusiness.PromotionStrategyService;
import com.nju.edu.erp.service.saleBusiness.SaleService;
import com.nju.edu.erp.service.warehouseBusiness.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
public class SaleServiceTest { // 该测试为集成测试，需要用到数据库，请大家连给定的测试数据库进行测试

    @Autowired
    WarehouseService warehouseService;
    @Autowired
    SaleService saleService;

    @Autowired
    SaleSheetDao saleSheetDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    WarehouseOutputSheetDao warehouseOutputSheetDao;

    @Autowired
    PromotionDao promotionDao;

    @Autowired
    PromotionStrategyService promotionStrategyService;

    @Autowired
    GiftSheetDao giftSheetDao;



    @Test
    public void warehouseServiceTest(){
        if(warehouseService==null){
            System.out.println("service也是空的");
        }else{
            System.out.println("service不是空的");
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void getSaleSheetByState() { // 测试按照状态获取销售单及其content是否成功
        List<SaleSheetVO> saleSheetByState = saleService.getSaleSheetByState(SaleSheetState.PENDING_LEVEL_2);
        Assertions.assertNotNull(saleSheetByState);
        Assertions.assertEquals(1, saleSheetByState.size());
        SaleSheetVO sheet1 = saleSheetByState.get(0);
        Assertions.assertNotNull(sheet1);
        Assertions.assertEquals("XSD-20220524-00003", sheet1.getId());

        List<SaleSheetContentVO> sheet1Content = sheet1.getSaleSheetContent();
        Assertions.assertNotNull(sheet1Content);
        Assertions.assertEquals(2, sheet1Content.size());
        sheet1Content.sort(Comparator.comparing(SaleSheetContentVO::getPid));
        Assertions.assertEquals("0000000000400000", sheet1Content.get(0).getPid());
        Assertions.assertEquals(0, sheet1Content.get(0).getTotalPrice().compareTo(BigDecimal.valueOf(280000.00)));
        Assertions.assertEquals("0000000000400001", sheet1Content.get(1).getPid());
        Assertions.assertEquals(0, sheet1Content.get(1).getTotalPrice().compareTo(BigDecimal.valueOf(380000.00)));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_exceptions_1() { // 一级审批不能直接到审批完成 (提示：可以以抛出异常的方式终止流程，这样就能触发事务回滚)
        try {
            saleService.approval("XSD-20220524-00004", SaleSheetState.SUCCESS);
        } catch (Exception ignore){
        } finally {
            SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00004");
            Assertions.assertEquals(SaleSheetState.PENDING_LEVEL_1,sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_exceptions_2() { // 二级审批不能回到一级审批
        try {
            saleService.approval("XSD-20220524-00003", SaleSheetState.PENDING_LEVEL_1);
        } catch (Exception ignore){
        } finally {
            SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00003");
            Assertions.assertEquals(SaleSheetState.PENDING_LEVEL_2,sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_failed() { // 测试审批失败
        saleService.approval("XSD-20220524-00003", SaleSheetState.FAILURE);
        SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00003");
        Assertions.assertEquals(SaleSheetState.FAILURE,sheet.getState());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_1() { // 测试一级审批
        saleService.approval("XSD-20220524-00004", SaleSheetState.PENDING_LEVEL_2);
        SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00004");
        Assertions.assertEquals(SaleSheetState.PENDING_LEVEL_2,sheet.getState());
    }
    @Test
    @Transactional
    @Rollback
    public  void makeSheetWithPromotionTest(){
        PromotionPO po = PromotionPO.builder()
                .kind("代金券")
                .rule("客户等级")
                .level(1)
                .voucher(BigDecimal.valueOf(120))
                .beginDate(new Date(122, Calendar.JUNE,10))
                .endDate(new Date(122, Calendar.OCTOBER,10))
                .id("1")
                .build();
        promotionStrategyService.addVoucher(po);
        PromotionPO po2 = PromotionPO.builder()
                .kind("折扣")
                .rule("客户等级")
                .level(1)
                .discount(80)
                .beginDate(new Date(122, Calendar.JUNE,10))
                .endDate(new Date(122, Calendar.OCTOBER,10))
                .id("2")
                .build();
        promotionStrategyService.addDiscount(po2);
        PromotionPO po3 = PromotionPO.builder()
                .kind("赠品")
                .rule("总金额")
                .amount(BigDecimal.valueOf(20))
                .giftId("0000000000400000")
                .giftQuantity(2)
                .beginDate(new Date(122, Calendar.JUNE,10))
                .endDate(new Date(122, Calendar.OCTOBER,10))
                .id("3")
                .build();
        promotionStrategyService.addGift(po3);
        PromotionPO po4 = PromotionPO.builder()
                .kind("代金券")
                .rule("商品组合")
                .amount(BigDecimal.valueOf(20))
                .composition1Id("0000000000400000")
                .composition2Id("0000000000400001")
                .voucher(BigDecimal.valueOf(10.0))
                .beginDate(new Date(122, Calendar.JUNE,10))
                .endDate(new Date(122, Calendar.OCTOBER,10))
                .id("4")
                .build();
        promotionStrategyService.addVoucher(po4);
        Assertions.assertEquals( promotionStrategyService.findAll().size(),4);

        UserVO userVO = UserVO.builder()
                .name("xiaoshoujingli")
                .role(Role.SALE_MANAGER)
                .build();
        List<SaleSheetContentVO> saleSheetContentVOS = new ArrayList<>();
        saleSheetContentVOS.add(SaleSheetContentVO.builder()
                .pid("0000000000400000")
                .quantity(50)
                .remark("Test1-product1")
                .unitPrice(BigDecimal.valueOf(3200))
                .build());
        saleSheetContentVOS.add(SaleSheetContentVO.builder()
                .pid("0000000000400001")
                .quantity(60)
                .remark("Test1-product2")
                .unitPrice(BigDecimal.valueOf(4200))
                .build());
        SaleSheetVO saleSheetVO = SaleSheetVO.builder()
                .saleSheetContent(saleSheetContentVOS)
                .supplier(2)
                .discount(BigDecimal.valueOf(0.8))
                .voucherAmount(BigDecimal.valueOf(300))
                .remark("Test1")
                .build();

        SaleSheetPO prevSheet = saleSheetDao.getLatestSheet();
        String realSheetId = IdGenerator.generateSheetId(prevSheet == null ? null : prevSheet.getId(), "XSD");

        saleService.makeSaleSheet(userVO, saleSheetVO);
        SaleSheetPO latestSheet = saleSheetDao.getLatestSheet();

        GiftSheetPO giftSheetPO = giftSheetDao.getLatest();
        Assertions.assertEquals("0000000000400000",giftSheetPO.getProductId());
        Assertions.assertEquals(2,giftSheetPO.getQuantity());


        Assertions.assertEquals(0,latestSheet.getFinalAmount().compareTo(BigDecimal.valueOf(329080.00)));

    }

//    @Test
//    @Transactional
//    @Rollback(value = true)
//    public void approval_2() { // 测试二级审批
//        // 二级审批成功之后需要进行
//        // 1. 修改单据状态
//        // 2. 更新商品表
//        // 3. 更新客户表
//        // 4. 新建出库草稿
//        saleService.approval("XSD-20220524-00003", SaleSheetState.SUCCESS);
//        SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00003");
//        Assertions.assertEquals(SaleSheetState.SUCCESS,sheet.getState());
//
//        Assertions.assertEquals(0, productDao.findById("0000000000400000").getRecentRp().compareTo(BigDecimal.valueOf(2800.00)));
//        Assertions.assertEquals(0, productDao.findById("0000000000400001").getRecentRp().compareTo(BigDecimal.valueOf(3800.00)));
//
//        Assertions.assertEquals(0, customerDao.findOneById(2).getReceivable().compareTo(BigDecimal.valueOf(4959100.00)));
//        List<WarehouseOutputSheetPO> draftSheets = warehouseOutputSheetDao.getDraftSheets(WarehouseOutputSheetState.DRAFT);
//        Assertions.assertNotNull(draftSheets);
//        Assertions.assertEquals(1, draftSheets.size());
//        WarehouseOutputSheetPO draftSheet = draftSheets.get(0);
//        Assertions.assertNotNull(draftSheet);
//        Assertions.assertEquals("XSD-20220524-00003",draftSheet.getSaleSheetId());
//    }
}