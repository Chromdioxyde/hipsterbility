package de.hsosnabrueck.hipsterbility.model.enums;


import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by Albert on 14.09.2014.
 */
@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum DeviceClass {
    PHONE("PHONE"),
    TABLET("TABLET"),
    PHABLET("PHABLET"),
    TV("TV"),
    WATCH("WATCH"),
    CAR("CAR"),
    OTHER("OTHER");

    private String name;

    DeviceClass(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
