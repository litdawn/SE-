package com.nju.edu.erp.service.customerBusiness;

import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.customer.CustomerPO;

import java.util.List;

public interface CustomerService {
    /**
     * 根据id更新客户信息
     * @param customerPO 客户信息
     */
    void updateCustomer(CustomerPO customerPO);

    /**
     * 根据type查找对应类型的客户
     * @param type 客户类型
     * @return 客户列表
     */
    List<CustomerPO> getCustomersByType(CustomerType type);

    /**
     * 根据id查找供应商
     * @param supplier
     * @return
     */
    CustomerPO findCustomerById(Integer supplier);

    /**
     * 新增一用户
     * @param customerPO 客户信息
     */
    void addNewCustomer(CustomerPO customerPO);

    /**
     * 根据id删除某一客户
     * @param id
     */
    void deleteCustomer(int id);

    List<Integer> getAllCustomerId();
}
