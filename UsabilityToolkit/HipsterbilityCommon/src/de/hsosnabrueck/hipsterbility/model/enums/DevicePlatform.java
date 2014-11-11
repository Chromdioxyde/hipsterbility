package de.hsosnabrueck.hipsterbility.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by Albert on 25.09.2014.
 */
//@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum DevicePlatform {
    ANDROID("ANDROID");

    private String name;

    DevicePlatform(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
