package com.bruce.travel.travels.been;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterBean implements Serializable {

    private String key;
    private String value;
    private boolean isSelected;

    public FilterBean() {
    }

    public FilterBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
