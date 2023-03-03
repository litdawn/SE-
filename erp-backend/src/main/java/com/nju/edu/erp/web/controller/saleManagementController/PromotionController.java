package com.nju.edu.erp.web.controller.saleManagementController;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.GiftSheetState;
import com.nju.edu.erp.model.po.sale.PromotionPO;
import com.nju.edu.erp.service.saleBusiness.GiftService;
import com.nju.edu.erp.service.saleBusiness.PromotionStrategyService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/promotion")
public class PromotionController {

    private final PromotionStrategyService promotionStrategyService;
    private final GiftService giftService;

    @Autowired
    PromotionController (PromotionStrategyService promotionStrategyService,GiftService giftService){
        this.promotionStrategyService = promotionStrategyService;
        this.giftService = giftService;
    }


    @Authorized(roles = {Role.GM, Role.ADMIN})
    @PostMapping(value = "/voucher")
    public Response addVoucher(@RequestBody PromotionPO promotionPO)  {
        promotionStrategyService.addVoucher(promotionPO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @PostMapping(value = "/discount")
    public Response addDiscount(@RequestBody PromotionPO promotionPO)  {
        promotionStrategyService.addDiscount(promotionPO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @PostMapping(value = "/gift")
    public Response addGift(@RequestBody PromotionPO promotionPO)  {
        promotionStrategyService.addGift(promotionPO);
        return Response.buildSuccess();
    }
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value ="/getPromotion")
    public Response findAll(){
        return Response.buildSuccess(promotionStrategyService.findAll());
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/getGiftSheetById")
    public Response getGiftSheetById(@RequestParam("GiftSheetId") String id)  {
        return Response.buildSuccess(giftService.getGiftSheetById(id));
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/approve")
    public Response approve(@RequestParam("GiftSheetId") String id,
                            @RequestParam("state")GiftSheetState state)  {
        giftService.approval(id,state);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/getGiftSheetByState")
    public Response getGiftSheetById(@RequestParam("state")GiftSheetState state)  {
        return Response.buildSuccess(giftService.getGiftSheetByState(state));
    }


}
