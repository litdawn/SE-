package com.nju.edu.erp.serviceImpl.saleBusinessImpl;

import com.nju.edu.erp.dao.saleDao.SaleReturnsSheetDao;
import com.nju.edu.erp.dao.saleDao.SaleSheetDao;
import com.nju.edu.erp.dao.warehouseDao.ProductDao;
import com.nju.edu.erp.dao.warehouseDao.WarehouseDao;
import com.nju.edu.erp.dao.warehouseDao.WarehouseOutputSheetContentDao;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.model.po.sale.SaleReturnsSheetContentPO;
import com.nju.edu.erp.model.po.sale.SaleReturnsSheetPO;
import com.nju.edu.erp.model.po.sale.SaleSheetContentPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import com.nju.edu.erp.model.po.warehouse.WarehouseOutputSheetContentPO;
import com.nju.edu.erp.model.vo.warehouse.ProductInfoVO;
import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.model.vo.sale.SaleReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.sale.SaleReturnsSheetVO;
import com.nju.edu.erp.service.customerBusiness.CustomerService;
import com.nju.edu.erp.service.warehouseBusiness.ProductService;
import com.nju.edu.erp.service.saleBusiness.SaleReturnsService;
import com.nju.edu.erp.service.warehouseBusiness.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@Service
public class SaleReturnsServiceImpl implements SaleReturnsService {

    SaleReturnsSheetDao saleReturnsSheetDao;

    ProductService productService;

    ProductDao productDao;

    SaleSheetDao saleSheetDao;

    CustomerService customerService;

    WarehouseService warehouseService;

    WarehouseDao warehouseDao;

    WarehouseOutputSheetContentDao warehouseOutputSheetContentDao;

    @Autowired
    public SaleReturnsServiceImpl(SaleReturnsSheetDao saleReturnsSheetDao, ProductService productService, CustomerService customerService, WarehouseService warehouseService, ProductDao productDao,SaleSheetDao saleSheetDao,WarehouseDao warehouseDao) {
        this.saleReturnsSheetDao = saleReturnsSheetDao;
        this.productService = productService;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
        this.productDao = productDao;
        this.saleSheetDao = saleSheetDao;
        this.warehouseDao =  warehouseDao;
    }

    /**
     * 制定销售退货单
     *
     * @param saleReturnsSheetVO 销售退货单
     */
    @Override
    @Transactional
    public void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO) {
        SaleReturnsSheetPO saleReturnsSheetPO = new SaleReturnsSheetPO();
        BeanUtils.copyProperties(saleReturnsSheetVO, saleReturnsSheetPO);
        // 此处根据制定单据人员确定操作员
//        saleReturnsSheetPO.setOperator(userVO.getName());
        saleReturnsSheetPO.setCreateTime(new Date());
        SaleReturnsSheetPO latest = saleReturnsSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSTHD");
        saleReturnsSheetPO.setId(id);
        saleReturnsSheetPO.setState(SaleReturnsSheetState.PENDING_LEVEL_1);
        BigDecimal voucherTotalAmount = BigDecimal.ZERO; //消费券作用在单个商品上的优惠价格的总和
        BigDecimal totalAmount = BigDecimal.ZERO; //实际退货的总金额
        List<SaleSheetContentPO> saleSheetContent = saleSheetDao.findContentBySheetId(saleReturnsSheetPO.getSaleSheetId());
        Map<String, SaleSheetContentPO> map = new HashMap<>();
        for(SaleSheetContentPO item : saleSheetContent) {
            map.put(item.getPid(), item);
        }
        List<SaleReturnsSheetContentPO> sContentPOList = new ArrayList<>();
        for(SaleReturnsSheetContentVO content : saleReturnsSheetVO.getSaleReturnsSheetContent()) {
            SaleReturnsSheetContentPO sContentPO = new SaleReturnsSheetContentPO();
            BeanUtils.copyProperties(content,sContentPO);
            sContentPO.setSaleReturnsSheetId(id);
            SaleSheetContentPO item = map.get(sContentPO.getPid());
            sContentPO.setUnitPrice(item.getUnitPrice());
            BigDecimal unitPrice = sContentPO.getUnitPrice();
            sContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(sContentPO.getQuantity())));
            sContentPOList.add(sContentPO);
            totalAmount = totalAmount.add(sContentPO.getTotalPrice());
            voucherTotalAmount = voucherTotalAmount.add(saleReturnsSheetPO.getVoucherAmount().multiply(unitPrice.divide(sContentPO.getTotalPrice(),2, RoundingMode.HALF_DOWN)));
        }
        totalAmount = totalAmount.multiply(saleReturnsSheetPO.getDiscount());
        totalAmount = totalAmount.subtract(voucherTotalAmount);
        saleReturnsSheetDao.saveBatch(sContentPOList);
        saleReturnsSheetPO.setTotalAmount(totalAmount);
        saleReturnsSheetDao.save(saleReturnsSheetPO);
    }

    /**
     * 根据状态获取销售退货单[不包括content信息](state == null 则获取所有进货退货单)
     *
     * @param state 销售退货单状态
     * @return 销售退货单
     */
    @Override
    public List<SaleReturnsSheetVO> getSaleReturnsSheetByState(SaleReturnsSheetState state) {
        List<SaleReturnsSheetVO> res = new ArrayList<>();
        List<SaleReturnsSheetPO> all;
        if(state == null) {
            all = saleReturnsSheetDao.findAll();
        } else {
            all = saleReturnsSheetDao.findAllByState(state);
        }
        for(SaleReturnsSheetPO po: all) {
            SaleReturnsSheetVO vo = new SaleReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleReturnsSheetContentPO> alll = saleReturnsSheetDao.findContentBySaleReturnsSheetId(po.getId());
            List<SaleReturnsSheetContentVO> vos = new ArrayList<>();
            for (SaleReturnsSheetContentPO p : alll) {
                SaleReturnsSheetContentVO v = new SaleReturnsSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSaleReturnsSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据销售退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param saleReturnsSheetId 进货退货单id
     * @param state           进货退货单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String saleReturnsSheetId, SaleReturnsSheetState state) {
        SaleReturnsSheetPO saleReturnsSheet = saleReturnsSheetDao.findOneById(saleReturnsSheetId);
        if(state.equals(SaleReturnsSheetState.FAILURE)) {
            if(saleReturnsSheet.getState() == SaleReturnsSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = saleReturnsSheetDao.updateState(saleReturnsSheetId, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SaleReturnsSheetState prevState;
            if(state.equals(SaleReturnsSheetState.SUCCESS)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_2;
            } else if(state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = saleReturnsSheetDao.updateStateV2(saleReturnsSheetId, prevState, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            if(state.equals(SaleReturnsSheetState.SUCCESS)) {
                // TODO 审批完成, 修改一系列状态
                // 销售退货单id， 关联的销售单id 【   销售退货单id->销售单id->出库单id->批次id】
//                Integer batchId = saleReturnsSheetDao.findBatchId(saleReturnsSheetId);

                //- 销售退货单id-pid， quantity 【批次id+pid -> 定位到库存的一个条目->库存加上quantity】
                //- 【 pid -> 定位到单位进价->Σ单位进价*quantity=要收回的钱->客户payable减去要收回的钱】
                List<SaleReturnsSheetContentPO> contents = saleReturnsSheetDao.findContentBySaleReturnsSheetId(saleReturnsSheetId);
//                BigDecimal receivableToDeduct = BigDecimal.ZERO;
                for (SaleReturnsSheetContentPO content:
                        contents) {
                    String pid = content.getPid();
                    Integer quantity = content.getQuantity();//需要退货的商品数量
                    List<WarehouseOutputSheetContentPO> warehouseOutputSheetContentPOs = warehouseOutputSheetContentDao.findByPidOrderBySalePricePos(pid);//由商品单价从低到高排序
                    for(WarehouseOutputSheetContentPO warehouseOutputSheetContentPO: warehouseOutputSheetContentPOs){
                        if(warehouseOutputSheetContentPO == null) throw new RuntimeException("单据发生错误！请联系管理员！");
                        if(warehouseOutputSheetContentPO.getQuantity() >= quantity) {
                            warehouseOutputSheetContentPO.setQuantity(quantity);
                            warehouseDao.addQuantity(warehouseOutputSheetContentPO);
                        }else{//该批次数量不够的话最多把自己卖出去的退回来
                            warehouseDao.addQuantity(warehouseOutputSheetContentPO);
                            quantity = quantity - warehouseOutputSheetContentPO.getQuantity();
                        }
                    }
                    if(quantity > 0){//还剩有商品未能退货
                        saleReturnsSheetDao.updateState(saleReturnsSheetId, SaleReturnsSheetState.FAILURE);
                        throw new RuntimeException("商品数量不足！审批失败！");
                    }else{
                        ProductInfoVO productInfoVO = productService.getOneProductByPid(pid);
                        productInfoVO.setQuantity(productInfoVO.getQuantity()+content.getQuantity());
                        productService.updateProduct(productInfoVO);
//                        receivableToDeduct = receivableToDeduct.add(content.get) ;
                    }
                }

                SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleReturnsSheet.getSaleSheetId());
                Integer supplier = saleSheetPO.getSupplier();
                CustomerPO customer = customerService.findCustomerById(supplier);

                customer.setReceivable(customer.getReceivable().add(saleReturnsSheet.getTotalAmount()));
                customerService.updateCustomer(customer);
            }
        }
    }
}