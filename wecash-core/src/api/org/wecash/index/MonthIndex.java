package org.wecash.index;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.wecash.model.Expense;
import org.wecash.model.compare.DateComparator;

public class MonthIndex extends AbstractIndex<Date, Expense> implements IIndex<Date, Expense> {
    public MonthIndex() {
        super(new DateComparator());
    }

    protected Set<Date> index(Expense e) {
        return newSet(monthOf(e.getDate()));
    }

    public Date monthOf(Date date) {
        int m = date.getMonth();
        int y = date.getYear();
        return newDate(m, y);
    }

    public int daysInMonth(Date month) {
        Calendar mycal = new GregorianCalendar();
        mycal.setTime(month);
        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public Date yesterday(Date day) {
        Calendar today = Calendar.getInstance();
        today.setTime(day);
        today.add(Calendar.DATE, -1);
        return new Date(today.getTimeInMillis());
    }
}
