package org.wecash.model.compare;

import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Date> {
    @Override
    public int compare(Date s1, Date s2) {
        long n1 = s1.getTime();
        long n2 = s2.getTime();
        if (n1 < n2)
            return -1;
        else if (n1 > n2)
            return 1;
        else
            return 0;
    }
}
