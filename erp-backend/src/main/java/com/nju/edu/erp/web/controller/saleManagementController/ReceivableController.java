package com.nju.edu.erp.web.controller.saleManagementController;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.vo.sale.ReceivableSheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.service.saleBusiness.ReceivableService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/receivable")
public class ReceivableController {

    private final ReceivableService receivableService;

    @Autowired
    public ReceivableController(ReceivableService receivableService) {
        this.receivableService = receivableService;
    }

    /**
     * 财务人员制定收款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/receivable-sheet-make")
    public Response makeReceipt(UserVO userVO, @RequestBody ReceivableSheetVO receivableSheetVO)  {
        receivableService.makeReceivableSheet(userVO, receivableSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 总经理审批
     * @param receivableSheetId 收款单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("receivableSheetId") String receivableSheetId,
                                   @RequestParam("state") ReceivableSheetState state)  {
        if(state.equals(ReceivableSheetState.FAILURE) || state.equals(ReceivableSheetState.SUCCESS)) {
            receivableService.approval(receivableSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }

    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @GetMapping(value = "/find-all-receivable-sheet")
    public Response findAllReceivable()  {
        return Response.buildSuccess(receivableService.getAllReceivableSheet());
    }
}
