package com.nju.edu.erp.serviceImpl.staffBusinessImpl;

import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl.CommissionPaymentCalculation;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl.GradeTaxCalculation;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl.MonthlyPaymentCalculation;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.Impl.YearlyPaymentCalculation;
import com.nju.edu.erp.serviceImpl.staffBusinessImpl.salaryStrategy.SalaryCalculation;
import com.nju.edu.erp.dao.staffDao.SalarySheetDao;
import com.nju.edu.erp.enums.SalaryRule;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.staff.SalarySheetPO;
import com.nju.edu.erp.model.vo.staff.SalarySheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.service.staffBusiness.SalaryService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

;

@Service
public class SalaryServiceImpl implements SalaryService {

    SalarySheetDao salarySheetDao;

    @Autowired
    public SalaryServiceImpl(SalarySheetDao salarySheetDao) {
        this.salarySheetDao = salarySheetDao;
    }

    /**
     * 制定工资单
     *
     * @param salarySheetVO 工资单
     */
    @Override
    @Transactional
    public void makeSalarySheet(UserVO userVO, SalarySheetVO salarySheetVO) {
        SalaryCalculation salaryCalculation = new SalaryCalculation(salarySheetVO);
        //税款都是基本工资*12来算
//        salarySheetVO.setTaxAmount(salaryCalculation.getTaxAmount());
        if (salarySheetVO.getPaymentSchedule()==SalaryRule.MONTHLY){//月薪制
            salaryCalculation.setPaymentCalculation(new MonthlyPaymentCalculation(new GradeTaxCalculation()));
            salarySheetVO.setRawAmount(salaryCalculation.getRawAmount());
            salarySheetVO.setTaxAmount(salaryCalculation.getTaxAmount());
            salarySheetVO.setFinalAmount(salaryCalculation.getFinalAmount());
        } else if (salarySheetVO.getPaymentSchedule()==SalaryRule.COMMISSIONED){//提成制
            salaryCalculation.setPaymentCalculation(new CommissionPaymentCalculation(new GradeTaxCalculation()));
            salarySheetVO.setRawAmount(salaryCalculation.getRawAmount());
            salarySheetVO.setTaxAmount(salaryCalculation.getTaxAmount());
            salarySheetVO.setFinalAmount(salaryCalculation.getFinalAmount());
        } else {
            salaryCalculation.setPaymentCalculation(new YearlyPaymentCalculation(new GradeTaxCalculation()));
            salarySheetVO.setRawAmount(salaryCalculation.getRawAmount());
            salarySheetVO.setTaxAmount(salaryCalculation.getTaxAmount());
            salarySheetVO.setFinalAmount(salaryCalculation.getFinalAmount());
        }
        SalarySheetPO salarySheetPO = new SalarySheetPO();
        BeanUtils.copyProperties(salarySheetVO, salarySheetPO);
        // 此处根据制定单据人员确定操作员
//        salarySheetPO.setOperator(userVO.getName());
        salarySheetPO.setCreateTime(new Date());
        SalarySheetPO latest = salarySheetDao.getLatestSheet();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "GZD");
        salarySheetPO.setId(id);
        salarySheetPO.setState(SalarySheetState.PENDING_LEVEL_2);
        salarySheetDao.saveSheet(salarySheetPO);
    }

    /**
     * 根据状态获取工资单(state == null 则获取所有工资单)
     *
     * @param state 工资单状态
     * @return 工资单
     */
    @Override
    public List<SalarySheetVO> getSalarySheetByState(SalarySheetState state) {
        List<SalarySheetVO> res = new ArrayList<>();
        List<SalarySheetPO> all;
        if(state == null) {
            all = salarySheetDao.findAllSheet();
        } else {
            all = salarySheetDao.findAllByState(state);
        }
        for(SalarySheetPO po: all) {
            SalarySheetVO vo = new SalarySheetVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据工资单id进行审批(state == "审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param salarySheetId 工资单id
     * @param state           工资单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String salarySheetId, SalarySheetState state) {
        if(state.equals(SalarySheetState.FAILURE)) {
//            SalarySheetPO salarySheet = salarySheetDao.findSheetById(salarySheetId);
//            if(salarySheet.getState() == SalarySheetState.SUCCESS) throw new RuntimeException("状态更新失败");
//            int effectLines = salarySheetDao.updateSheetState(salarySheetId, state);
//            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            salarySheetDao.updateSheetState(salarySheetId, state);
        } else {
            //SalarySheetPO salarySheet = salarySheetDao.findSheetById(salarySheetId);
            //修改单据状态
            salarySheetDao.updateSheetState(salarySheetId, state);

        }
    }

    /**
     * 根据工资单Id搜索工资单信息
     * @param salarySheetId 工资单Id
     * @return 工资单全部信息
     */
    @Override
    public SalarySheetVO getSalarySheetById(String salarySheetId) {
        SalarySheetPO salarySheetPO = salarySheetDao.findSheetById(salarySheetId);
        if(salarySheetPO == null) return null;
        SalarySheetVO pVO = new SalarySheetVO();
        BeanUtils.copyProperties(salarySheetPO, pVO);
        return pVO;
    }

    public void makeSalaryRule(String staffId, SalaryRule rule){
        salarySheetDao.makeSalaryRule(staffId,rule);
    }

    public BigDecimal checkPrevSalary(String staffId){
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        List<SalarySheetPO> pos = salarySheetDao.findSheetByIdTime(staffId, year);
        BigDecimal prevSalary = BigDecimal.ZERO;
        for (SalarySheetPO po: pos){
            prevSalary = prevSalary.add(po.getFinalAmount());
        }
        return prevSalary;
    }

    /**
     * 查找所有工资单
     * @return
     */
    @Override
    public List<SalarySheetPO> findAllSheet() {
        return salarySheetDao.findAllSheet();
    }


}