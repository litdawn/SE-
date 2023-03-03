package com.nju.edu.erp.serviceImpl.customerBusinessImpl;

import com.nju.edu.erp.dao.customerDao.CustomerDao;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.service.customerBusiness.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * 根据id更新客户信息
     *
     * @param customerPO 客户信息
     */
    @Override
    public void updateCustomer(CustomerPO customerPO) {
        customerDao.updateOne(customerPO);
    }

    /**
     * 根据type查找对应类型的客户
     *
     * @param type 客户类型
     * @return 客户列表
     */
    @Override
    public List<CustomerPO> getCustomersByType(CustomerType type) {

        return customerDao.findAllByType(type);
    }

    @Override
    public CustomerPO findCustomerById(Integer supplier) {
        return customerDao.findOneById(supplier);
    }

    /**
     * 新增一用户
     * @param customerPO 客户信息
     */
    public void addNewCustomer(CustomerPO customerPO){customerDao.addOne(customerPO);}

    /**
     * 新增一用户
     *
     * @param id 客户id
     */
    public void deleteCustomer(int id) {
        customerDao.deleteOne(id);
    }

    public List<Integer> getAllCustomerId(){
        List<CustomerPO> all = customerDao.findAll();
        List<Integer> res = new ArrayList<>();
        for(CustomerPO po: all){
            res.add(po.getId());
        }
        return res;
    }
}
