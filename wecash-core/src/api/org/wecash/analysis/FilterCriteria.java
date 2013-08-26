package org.wecash.analysis;

import java.util.Date;
import java.util.Set;

public class FilterCriteria {
    public FilterCriteria(Set<String> categories, Date from, Date to) {
        this.categories = categories;
        this.from = from;
        this.to = to;
    }

    protected Set<String> categories;
    protected Date from;
    protected Date to;
}