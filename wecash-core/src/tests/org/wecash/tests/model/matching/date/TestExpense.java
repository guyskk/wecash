package org.wecash.tests.model.matching.date;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.wecash.model.Expense;
import org.wecash.model.compare.ExpenseDateComparator;
import org.wecash.utils.DateUtils;

public class TestExpense {
    @Test
    public void testEquality(){
        Expense e1 = new Expense(DateUtils.newDate(2012, 10, 02), "Virement Emis                       Web Compte Commun", -940);
        Expense e2 = new Expense(DateUtils.newDate(2012, 10, 20), "Virement Emis                       Web Compte Commun", -1141);
        Expense e3 = new Expense(DateUtils.newDate(2012, 10, 20), "Virement Emis                       Web Compte Commun", -941);
        
        assertFalse(e1.equals(e2));
        assertFalse(e1.equals(e3));
        assertFalse(e2.equals(e3));
        
        
    }
    
    {
        Set<Expense> s = new TreeSet<Expense>(new ExpenseDateComparator());
        /*s.add(e1);
        s.add(e2);
        s.add(e3);
        //assertEquals(3, s.size());
        System.out.println(s);*/
    }
    
    /*
Mon Dec 03 00:00:00 CET 2012 | Virement Emis                       Web Compte Commun : -940.0
Tue Oct 02 00:00:00 CEST 2012 | Virement Emis                       Web Compte Commun : -940.0
Sat Oct 20 00:00:00 CEST 2012 | Virement Emis                       Web Compte Commun : -1191.0
Sat Oct 20 00:00:00 CEST 2012 | Virement Emis                       Web Compte Commun : -941.0
Mon Feb 04 00:00:00 CET 2013 | Virement Emis                       Web Compte Commun : -940.0
Fri Nov 02 00:00:00 CET 2012 | Virement Emis                       Web Compte Commun : -940.0
Wed Jan 02 00:00:00 CET 2013 | Virement Emis                       Web Compte Commun : -940.0
---
Tue Oct 02 00:00:00 CEST 2012 | Virement Emis                       Web Compte Commun : -940.0
Fri Nov 02 00:00:00 CET 2012 | Virement Emis                       Web Compte Commun : -940.0
Mon Dec 03 00:00:00 CET 2012 | Virement Emis                       Web Compte Commun : -940.0
Wed Jan 02 00:00:00 CET 2013 | Virement Emis                       Web Compte Commun : -940.0
Mon Feb 04 00:00:00 CET 2013 | Virement Emis                       Web Compte Commun : -940.0
     */
}
