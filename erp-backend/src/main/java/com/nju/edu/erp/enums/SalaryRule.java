package com.nju.edu.erp.enums;

public enum SalaryRule implements BaseEnum<SalaryRule,String>{
    MONTHLY("月薪制"),
    YEARLY("年薪制"),
    COMMISSIONED("提成制");
    private final String value;

    SalaryRule(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
