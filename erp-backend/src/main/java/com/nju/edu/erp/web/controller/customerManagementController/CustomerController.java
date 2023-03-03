package com.nju.edu.erp.web.controller.customerManagementController;

import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.service.customerBusiness.CustomerService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/findByType")
    public Response findByType(@RequestParam CustomerType type) {
        return Response.buildSuccess(customerService.getCustomersByType(type));
    }
    @PostMapping("/addNewCustomer")
    public Response addNewCustomer(@RequestParam(value = "id") int id,
                                   @RequestParam(value = "type") String type,
                                   @RequestParam(value="level") int level,
                                   @RequestParam(value ="name") String name,
                                   @RequestParam(value = "phone")String phone,
                                   @RequestParam(value="address")String address,
                                   @RequestParam(value = "zipcode")String zipcode,
                                   @RequestParam(value = "email")String email,
                                   @RequestParam(value = "line_of_credit") BigDecimal line_of_credit,
                                   @RequestParam(value ="receivale") BigDecimal receivable,
                                   @RequestParam(value="payable")BigDecimal payable,
                                   @RequestParam(value = "operator")String operator){
        CustomerPO customerPO=new CustomerPO (id, type, level, name, phone, address, zipcode, email, line_of_credit, receivable, payable, operator);
        customerService.addNewCustomer(customerPO);
        return Response.buildSuccess();
    }
    @DeleteMapping ("/deleteCustomer")
    public Response deleteCustomer(@RequestParam(value = "id") int id){
        customerService.deleteCustomer(id);
        return Response.buildSuccess();
    }

    @GetMapping(value = "/show-all-customer-id")
    public Response findAllCustomerId()  {
        return Response.buildSuccess(customerService.getAllCustomerId());
    }
}
