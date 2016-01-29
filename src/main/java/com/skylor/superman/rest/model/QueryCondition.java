package com.skylor.superman.rest.model;

import com.skylor.superman.rest.enumeration.QueryComparator;

/**
 * Created by darcy on 2016/1/29.
 */
public class QueryCondition {
    private QueryComparator comparator;

    private String propertyName;

    private String propertyValue;

    public QueryCondition(String propertyName, String propertyValue, QueryComparator comparator) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.comparator = comparator;
    }

    public QueryComparator getComparator() {
        return comparator;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }
}
