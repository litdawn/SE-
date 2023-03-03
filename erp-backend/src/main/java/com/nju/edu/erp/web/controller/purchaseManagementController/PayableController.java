package com.nju.edu.erp.web.controller.purchaseManagementController;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.model.vo.purchase.PayableSheetVO;
import com.nju.edu.erp.model.vo.staff.UserVO;
import com.nju.edu.erp.service.purchaseBusiness.PayableService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/payable")
public class PayableController {

    private final PayableService payableService;

    @Autowired
    public PayableController(PayableService payableService) {
        this.payableService = payableService;
    }

    /**
     * 财务人员制定付款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/payable-sheet-make")
    public Response makePaymentOrder(UserVO userVO, @RequestBody PayableSheetVO payableSheetVO)  {
        payableService.makePayableSheet(userVO, payableSheetVO);
        return Response.buildSuccess();
    }


    /**
     * 总经理审批
     * @param payableSheetId 付款单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("payableSheetId") String payableSheetId,
                                   @RequestParam("state") PayableSheetState state)  {
        if(state.equals(PayableSheetState.FAILURE) || state.equals(PayableSheetState.SUCCESS)) {
            payableService.approval(payableSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }


    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @GetMapping(value = "/find-all-payable-sheet")
    public Response findAllPayable()  {
        return Response.buildSuccess(payableService.getAllPayableSheet());
    }
}
