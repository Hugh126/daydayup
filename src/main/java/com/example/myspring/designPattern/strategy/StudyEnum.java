package com.example.myspring.designPattern.strategy;

public enum StudyEnum {

    mathematics,
    chinese,
    English,
    Geography,
    history;

    public static StudyEnum getWithValue(String type) {
        for (StudyEnum item : StudyEnum.values()) {
            if (item.name().equals(type)) {
                return item;
            }
        }
        return null;
    }

}
