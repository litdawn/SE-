package com.nju.edu.erp.dao.staffDao;


import com.nju.edu.erp.model.po.staff.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {

    User findByUsernameAndPassword(String username, String password);

    int createUser(User user);

    User findByUsername(String username);

    List<User> findAllUser();
}
