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
     * ?????????????????????
     *
     * @param saleReturnsSheetVO ???????????????
     */
    @Override
    @Transactional
    public void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO) {
        SaleReturnsSheetPO saleReturnsSheetPO = new SaleReturnsSheetPO();
        BeanUtils.copyProperties(saleReturnsSheetVO, saleReturnsSheetPO);
        // ?????????????????????????????????????????????
//        saleReturnsSheetPO.setOperator(userVO.getName());
        saleReturnsSheetPO.setCreateTime(new Date());
        SaleReturnsSheetPO latest = saleReturnsSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSTHD");
        saleReturnsSheetPO.setId(id);
        saleReturnsSheetPO.setState(SaleReturnsSheetState.PENDING_LEVEL_1);
        BigDecimal voucherTotalAmount = BigDecimal.ZERO; //?????????????????????????????????????????????????????????
        BigDecimal totalAmount = BigDecimal.ZERO; //????????????????????????
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
     * ?????????????????????????????????[?????????content??????](state == null ??????????????????????????????)
     *
     * @param state ?????????????????????
     * @return ???????????????
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
     * ?????????????????????id????????????(state == "???????????????"/"????????????"/"????????????")
     * ???controller?????????????????????
     *
     * @param saleReturnsSheetId ???????????????id
     * @param state           ?????????????????????????????????
     */
    @Override
    @Transactional
    public void approval(String saleReturnsSheetId, SaleReturnsSheetState state) {
        SaleReturnsSheetPO saleReturnsSheet = saleReturnsSheetDao.findOneById(saleReturnsSheetId);
        if(state.equals(SaleReturnsSheetState.FAILURE)) {
            if(saleReturnsSheet.getState() == SaleReturnsSheetState.SUCCESS) throw new RuntimeException("??????????????????");
            int effectLines = saleReturnsSheetDao.updateState(saleReturnsSheetId, state);
            if(effectLines == 0) throw new RuntimeException("??????????????????");
        } else {
            SaleReturnsSheetState prevState;
            if(state.equals(SaleReturnsSheetState.SUCCESS)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_2;
            } else if(state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("??????????????????");
            }
            int effectLines = saleReturnsSheetDao.updateStateV2(saleReturnsSheetId, prevState, state);
            if(effectLines == 0) throw new RuntimeException("??????????????????");
            if(state.equals(SaleReturnsSheetState.SUCCESS)) {
                // TODO ????????????, ?????????????????????
                // ???????????????id??? ??????????????????id ???   ???????????????id->?????????id->?????????id->??????id???
//                Integer batchId = saleReturnsSheetDao.findBatchId(saleReturnsSheetId);

                //- ???????????????id-pid??? quantity ?????????id+pid -> ??????????????????????????????->????????????quantity???
                //- ??? pid -> ?????????????????????->??????????????*quantity=???????????????->??????payable????????????????????????
                List<SaleReturnsSheetContentPO> contents = saleReturnsSheetDao.findContentBySaleReturnsSheetId(saleReturnsSheetId);
//                BigDecimal receivableToDeduct = BigDecimal.ZERO;
                for (SaleReturnsSheetContentPO content:
                        contents) {
                    String pid = content.getPid();
                    Integer quantity = content.getQuantity();//???????????????????????????
                    List<WarehouseOutputSheetContentPO> warehouseOutputSheetContentPOs = warehouseOutputSheetContentDao.findByPidOrderBySalePricePos(pid);//?????????????????????????????????
                    for(WarehouseOutputSheetContentPO warehouseOutputSheetContentPO: warehouseOutputSheetContentPOs){
                        if(warehouseOutputSheetContentPO == null) throw new RuntimeException("??????????????????????????????????????????");
                        if(warehouseOutputSheetContentPO.getQuantity() >= quantity) {
                            warehouseOutputSheetContentPO.setQuantity(quantity);
                            warehouseDao.addQuantity(warehouseOutputSheetContentPO);
                        }else{//???????????????????????????????????????????????????????????????
                            warehouseDao.addQuantity(warehouseOutputSheetContentPO);
                            quantity = quantity - warehouseOutputSheetContentPO.getQuantity();
                        }
                    }
                    if(quantity > 0){//???????????????????????????
                        saleReturnsSheetDao.updateState(saleReturnsSheetId, SaleReturnsSheetState.FAILURE);
                        throw new RuntimeException("????????????????????????????????????");
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