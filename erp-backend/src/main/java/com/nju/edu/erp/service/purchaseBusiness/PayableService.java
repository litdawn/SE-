package com.nju.edu.erp.service.purchaseBusiness;

import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.model.vo.purchase.PayableSheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;

import java.util.List;

public interface PayableService {
    /**
     * 制定付款单
     * @param payableSheetVO 付款单
     */
    void makePayableSheet(UserVO userVO, PayableSheetVO payableSheetVO);

    /**
     * 根据付款单id进行审批(state == "审批完成"/"审批失败")
     * 在controller层进行权限控制
     * @param payableSheetId 付款单id
     * @param state 付款单修改后的状态
     */
    void approval(String payableSheetId, PayableSheetState state);

    List<PayableSheetVO> getAllPayableSheet();
}
