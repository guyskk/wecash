package org.wecash.model.compare;

import java.util.Comparator;

import org.wecash.model.Expense;

/**
 * Warning: as this comparator is working on date only, it should not be
 * used in a TreeSet, otherwise two expense at the same date would appear only
 * once.
 * 
 * @author Martin
 */
public class ExpenseDateComparator implements Comparator<Expense>{
    @Override
    public int compare(Expense e0, Expense e1) {
        if(e0.getDate()==null || e1.getDate()==null)
            return 1;
        else
            return e0.getDate().compareTo(e1.getDate());
    }
}
