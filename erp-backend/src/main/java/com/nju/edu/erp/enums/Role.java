package com.nju.edu.erp.enums;


public enum Role implements BaseEnum<Role, String>{

    INVENTORY_MANAGER("INVENTORY_MANAGER"), //库存管理人员
    SALE_STAFF("SALE_STAFF"), // 进货销售人员
    FINANCIAL_STAFF("FINANCIAL_STAFF"), // 财务人员
    SALE_MANAGER("SALE_MANAGER"), //销售经理
    HR("HR"), // 人力资源人员
    GM("GM"), // 总经理
    ADMIN("ADMIN"); // 最高权限;

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

}
