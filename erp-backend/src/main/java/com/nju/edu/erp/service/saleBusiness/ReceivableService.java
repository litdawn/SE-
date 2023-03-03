package com.nju.edu.erp.service.saleBusiness;

import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.vo.sale.ReceivableSheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;

import java.util.List;

public interface ReceivableService {
    /**
     * 制定收款单
     * @param receivableSheetVO 收款单
     */
    void makeReceivableSheet(UserVO userVO, ReceivableSheetVO receivableSheetVO);

    /**
     * 根据收款单id进行审批(state == "审批完成"/"审批失败")
     * 在controller层进行权限控制
     * @param receivableSheetId 收款单id
     * @param state 收款单修改后的状态
     */
    void approval(String receivableSheetId, ReceivableSheetState state);

    List<ReceivableSheetVO> getAllReceivableSheet();

}
