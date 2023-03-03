package com.nju.edu.erp.serviceImpl.purchaseBusinessImpl;

import com.nju.edu.erp.dao.overallManagementDao.CompanyBankAccountDao;
import com.nju.edu.erp.dao.purchaseDao.PayableSheetDao;
import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.model.po.purchase.PayableSheetContentPO;
import com.nju.edu.erp.model.po.purchase.PayableSheetPO;
import com.nju.edu.erp.model.po.sale.SaleSheetContentPO;
import com.nju.edu.erp.model.vo.purchase.PayableSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PayableSheetVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.service.customerBusiness.CustomerService;
import com.nju.edu.erp.service.purchaseBusiness.PayableService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PayableServiceImpl implements PayableService {

    private final PayableSheetDao payableSheetDao;

    private final CustomerService customerService;

    private final CompanyBankAccountDao companyBankAccountDao;

    @Autowired
    public PayableServiceImpl(PayableSheetDao payableSheetDao, CompanyBankAccountDao companyBankAccountDao, CustomerService customerService){
        this.payableSheetDao = payableSheetDao;
        this.customerService = customerService;
        this.companyBankAccountDao = companyBankAccountDao;
    }

    /**
     * 制定收款单
     *
     * @param payableSheetVO 收款单
     */
    @Override
    @Transactional
    public void makePayableSheet(UserVO userVO, PayableSheetVO payableSheetVO){
        PayableSheetPO payableSheetPO = new PayableSheetPO();
        BeanUtils.copyProperties(payableSheetVO, payableSheetPO);
        // 此处根据制定单据人员确定操作员
//        payableSheetPO.setOperator(userVO.getName());
        payableSheetPO.setCreateTime(new Date());
        PayableSheetPO latest = payableSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XJFYD");
        payableSheetPO.setId(id);
        payableSheetPO.setState(PayableSheetState.PENDING_LEVEL_2);
        List<PayableSheetContentPO> pContentPOList = new ArrayList<>();
        for(PayableSheetContentVO content : payableSheetVO.getPayableContent()) {
            PayableSheetContentPO pContentPO = new PayableSheetContentPO();
            BeanUtils.copyProperties(content,pContentPO);
            pContentPO.setSheetId(id);
            pContentPOList.add(pContentPO);
        }
        payableSheetDao.saveBatch(pContentPOList);
        payableSheetDao.save(payableSheetPO);
    }

    /**
     * 根据付款单id进行审批(state == "审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param payableSheetId 付款单id
     * @param state           付款单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String payableSheetId, PayableSheetState state){
        if(state.equals(PayableSheetState.FAILURE)) {
            payableSheetDao.updateState(payableSheetId, state);
        } else {
            payableSheetDao.updateState(payableSheetId, state);
            //更新客户的payable
            PayableSheetPO payableSheet = payableSheetDao.findOneById(payableSheetId);
            CustomerPO customerPO = customerService.findCustomerById(payableSheet.getCustomerId());
            customerPO.setPayable(customerPO.getPayable().add(payableSheet.getTotalAmount()));
            customerService.updateCustomer(customerPO);
            companyBankAccountDao.reduceAccountMoney(payableSheet.getBankAccount(), payableSheet.getTotalAmount());
        }
    }


    public List<PayableSheetVO> getAllPayableSheet(){
        List<PayableSheetVO> res = new ArrayList<>();
        List<PayableSheetPO> all;
        all = payableSheetDao.findAll();
        for(PayableSheetPO po: all) {
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
}
