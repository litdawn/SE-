package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.customerDao.CustomerDao;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.service.customerBusiness.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    CustomerService customerService;

    @Test
    @Transactional
    public void addNewCustomerTest(){
        CustomerPO customerPO = CustomerPO.builder()
                .id(3)
                .type("供应商")
                .level(1)
                .name("dcc")
                .phone("14256")
                .address("南")
                .zipcode("153465")
                .email("gsfyguhi@ghsj.com")
                .lineOfCredit(BigDecimal.valueOf(2349900.00))
                .receivable(BigDecimal.valueOf(87654.00))
                .payable(BigDecimal.valueOf(987.10))
                .operator("cu")
                .build();

        customerService.addNewCustomer(customerPO);


        CustomerPO result = customerService.findCustomerById(3);
        Assertions.assertEquals(result.getName(), customerPO.getName());
    }

    @Test
    @Transactional
    public void deleteCustomerTest(){
        CustomerPO customerPO = CustomerPO.builder()
                .id(3)
                .type("供应商")
                .level(1)
                .name("dcc")
                .phone("14256")
                .address("南")
                .zipcode("153465")
                .email("gsfyguhi@ghsj.com")
                .lineOfCredit(BigDecimal.valueOf(2349900.00))
                .receivable(BigDecimal.valueOf(87654.00))
                .payable(BigDecimal.valueOf(987.10))
                .operator("cu")
                .build();

        customerService.addNewCustomer(customerPO);


        CustomerPO result = customerService.findCustomerById(3);
        Assertions.assertEquals(result.getName(), customerPO.getName());

        customerService.deleteCustomer(3);
        CustomerPO deleteResult = customerService.findCustomerById(3);
        Assertions.assertNull(deleteResult);

    }
}
