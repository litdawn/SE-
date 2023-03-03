package com.nju.edu.erp.dao.staffDao;

import com.nju.edu.erp.model.po.staff.AnnualBonusPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@Mapper
public interface AnnualBonusDao {
    /**
     * 设置年终奖
     * @param annualBonusPO
     * @return 影响的行数
     */
    int save(AnnualBonusPO annualBonusPO);

    BigDecimal getAmount(String staffId);
}
