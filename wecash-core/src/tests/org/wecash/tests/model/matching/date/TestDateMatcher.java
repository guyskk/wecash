package org.wecash.tests.model.matching.date;
import static org.junit.Assert.*;

import org.junit.Test;
import org.wecash.model.Expense;
import org.wecash.model.matching.date.DateMatcher;
import org.wecash.model.matching.date.IDateMatcher;
import org.wecash.utils.DateUtils;


public class TestDateMatcher {
    @Test
    public void testDateMatching(){
        Expense e1 = new Expense(DateUtils.newDate(2012, 0, 1),  "e1", 10);
        Expense e2 = new Expense(DateUtils.newDate(2013, 0, 1),  "e2", 10);
        Expense e3 = new Expense(DateUtils.newDate(2013, 0, 15), "e3", 10);
        Expense e4 = new Expense(DateUtils.newDate(2013, 1, 1),  "e4", 10);
        Expense e5 = new Expense(DateUtils.newDate(2013, 1, 15), "e5", 10);
        
        IDateMatcher m1 = new DateMatcher(DateUtils.newDate(2013, 0, 1), DateUtils.newDate(2013, 1, 1));
        assertFalse(m1.accepts(e1));
        assertTrue(m1.accepts(e2));
        assertTrue(m1.accepts(e3));
        assertTrue(m1.accepts(e4));
        assertFalse(m1.accepts(e5));

        IDateMatcher m2 = new DateMatcher(DateUtils.newDate(2013, 0, 1), null);
        assertFalse(m2.accepts(e1));
        assertTrue(m2.accepts(e5));
        
        IDateMatcher m3 = new DateMatcher(null, DateUtils.newDate(2013, 1, 1));
        assertTrue(m3.accepts(e1));
        assertFalse(m3.accepts(e5));
    }
}
