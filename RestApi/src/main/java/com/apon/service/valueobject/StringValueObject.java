package com.apon.service.valueobject;

public class StringValueObject {
    private String value;

    public StringValueObject(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
