package com.nju.edu.erp.service.overallManagementBusiness;

import com.nju.edu.erp.model.vo.overallManagement.BusinessConditionSheetVO;
import org.springframework.stereotype.Service;

@Service
public interface BusinessConditionService {
    /**
     * 根据起始时间，结束时间
     * @param
     * @return 经营情况
     */
    BusinessConditionSheetVO getBusinessConditionSheet(String startTime, String endTime);
}
