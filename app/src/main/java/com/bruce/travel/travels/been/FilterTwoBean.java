package com.bruce.travel.travels.been;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterTwoBean implements Serializable {

    private String type;
    private List<FilterBean> list;
    private boolean isSelected;
    private FilterBean selectedFilterEntity;

    public FilterTwoBean() {
    }

    public FilterTwoBean(String type, List<FilterBean> list) {
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FilterBean getSelectedFilterEntity() {
        return selectedFilterEntity;
    }

    public void setSelectedFilterEntity(FilterBean selectedFilterEntity) {
        this.selectedFilterEntity = selectedFilterEntity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<FilterBean> getList() {
        return list;
    }

    public void setList(List<FilterBean> list) {
        this.list = list;
    }
}
