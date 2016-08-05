package com.bruce.travel.travels.model;

import java.util.Comparator;

/**
 * Created by sunfusheng on 16/4/25.
 */
public class TravelsEntityComparator implements Comparator<TravelsEntity> {

    @Override
    public int compare(TravelsEntity lhs, TravelsEntity rhs) {
        return rhs.getRank() - lhs.getRank();
    }
}
