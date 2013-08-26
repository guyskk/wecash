package org.wecash.model.aggregator;

import java.util.HashMap;

import org.wecash.model.aggregator.MonthStatistics.MonthStat;

public class MonthStatistics extends HashMap<MonthStat,Double>{
    private static final long serialVersionUID = -8430298512389492103L;

    public enum MonthStat{
        SOLDE,
        NUMBER_OF_NEGATIVE_DAYS
    }
}
