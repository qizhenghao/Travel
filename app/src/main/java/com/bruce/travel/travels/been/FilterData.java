package com.bruce.travel.travels.been;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterData implements Serializable {

    private List<FilterTwoBean> category;
    private List<FilterBean> sorts;
    private List<FilterBean> filters;

    public List<FilterTwoBean> getCategory() {
        return category;
    }

    public void setCategory(List<FilterTwoBean> category) {
        this.category = category;
    }

    public List<FilterBean> getSorts() {
        return sorts;
    }

    public void setSorts(List<FilterBean> sorts) {
        this.sorts = sorts;
    }

    public List<FilterBean> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterBean> filters) {
        this.filters = filters;
    }
}
