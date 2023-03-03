package com.nju.edu.erp.serviceImpl.saleBusinessImpl;

import com.nju.edu.erp.dao.saleDao.ReceivableSheetDao;
import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.model.po.purchase.PayableSheetContentPO;
import com.nju.edu.erp.model.po.sale.ReceivableSheetContentPO;
import com.nju.edu.erp.model.po.sale.ReceivableSheetPO;
import com.nju.edu.erp.model.vo.purchase.PayableSheetContentVO;
import com.nju.edu.erp.model.vo.sale.ReceivableSheetContentVO;
import com.nju.edu.erp.model.vo.sale.ReceivableSheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.service.customerBusiness.CustomerService;
import com.nju.edu.erp.service.saleBusiness.ReceivableService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReceivableServiceImpl implements ReceivableService {

    ReceivableSheetDao receivableSheetDao;

    CustomerService customerService;

    @Autowired
    public ReceivableServiceImpl(ReceivableSheetDao receivableSheetDao, CustomerService customerService){
        this.receivableSheetDao = receivableSheetDao;
        this.customerService = customerService;
    }

    /**
     * 制定收款单
     *
     * @param receivableSheetVO 收款单
     */
    @Override
    @Transactional
    public void makeReceivableSheet(UserVO userVO, ReceivableSheetVO receivableSheetVO){
        ReceivableSheetPO receivableSheetPO = new ReceivableSheetPO();
        BeanUtils.copyProperties(receivableSheetVO, receivableSheetPO);
        // 此处根据制定单据人员确定操作员
//        receivableSheetPO.setOperator(userVO.getName());
        receivableSheetPO.setCreateTime(new Date());
        ReceivableSheetPO latest = receivableSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "SKD");
        receivableSheetPO.setId(id);
        receivableSheetPO.setState(ReceivableSheetState.PENDING_LEVEL_2);
        List<ReceivableSheetContentPO> pContentPOList = new ArrayList<>();
        for(ReceivableSheetContentVO content : receivableSheetVO.getReceivableContent()) {
            ReceivableSheetContentPO pContentPO = new ReceivableSheetContentPO();
            BeanUtils.copyProperties(content,pContentPO);
            pContentPO.setSheetId(id);
            pContentPOList.add(pContentPO);
        }
        receivableSheetDao.saveBatch(pContentPOList);
        receivableSheetDao.save(receivableSheetPO);
    }

    /**
     * 根据收款单id进行审批(state == "审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param receivableSheetId 收款单id
     * @param state           收款单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String receivableSheetId, ReceivableSheetState state){
        if(state.equals(ReceivableSheetState.FAILURE)) {
            receivableSheetDao.updateState(receivableSheetId, state);
        } else {
            receivableSheetDao.updateState(receivableSheetId, state);
            //更新客户的receivable
            ReceivableSheetPO receivableSheet = receivableSheetDao.findOneById(receivableSheetId);
            CustomerPO customerPO = customerService.findCustomerById(receivableSheet.getCustomerId());
            customerPO.setReceivable(customerPO.getReceivable().add(receivableSheet.getTotalAmount()));
            customerService.updateCustomer(customerPO);
        }
    }

    public List<ReceivableSheetVO> getAllReceivableSheet(){
        List<ReceivableSheetVO> res = new ArrayList<>();
        List<ReceivableSheetPO> all;
        all = receivableSheetDao.findAll();
        for(ReceivableSheetPO po: all) {
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
}
