package org.wecash.model.aggregator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wecash.index.MonthIndex;
import org.wecash.model.Expense;
import org.wecash.model.aggregator.MonthStatistics.MonthStat;
import org.wecash.model.compare.ExpenseDateComparator;

public class MonthStatisticsProcessor {
    public Map<Date, MonthStatistics> getNegativeDaysPerMonth(Collection<Expense> expenses) {
        // get date ordered list
        List<Expense> dateOrderedExpenses = new ArrayList<Expense>(expenses);
        Collections.sort(dateOrderedExpenses, new ExpenseDateComparator());

        // assert solde is the first
        Expense firstExpense = null;
        for (Expense e : dateOrderedExpenses) {
            if (!Double.isNaN(e.getSolde())) {
                firstExpense = e;
                break;
            }
        }
        if (dateOrderedExpenses.indexOf(firstExpense) > 0) {
            dateOrderedExpenses.remove(firstExpense);
            dateOrderedExpenses.add(0, firstExpense);
        }

        // rollback solde for each expense
        for (int i = 1; i < dateOrderedExpenses.size(); i++) {
            Expense prev = dateOrderedExpenses.get(i - 1);
            Expense current = dateOrderedExpenses.get(i);
            double currentSolde = prev.getSolde() - prev.getValue();
            current.setSolde(currentSolde);
            // System.out.println(current + "  " + current.getSolde());
        }

        MonthIndex monthIndex = new MonthIndex();
        monthIndex.index(expenses);

        Map<Date, MonthStatistics> monthStatistics = new HashMap<Date, MonthStatistics>();
        Map<Date, Double> dayScores = new HashMap<Date, Double>();

        for (Date month : monthIndex.getCategories()) {
            MonthStatistics stats = new MonthStatistics();

            //System.out.println(month);
            
            // count negative days of each month
            int ds = monthIndex.daysInMonth(month);
            double nNegatif = 0;
            Date day = null;
            for (int d = 1; d <= ds; d++) {
                // Calcule le score du jour
                day = monthIndex.newDate(d, month.getMonth(), month.getYear());
                List<Expense> dayExp = getExpenses(expenses, day);
                double dayScore = computeDayScore(dayExp);

                // si dayScore non défini, chopper celui de la veille
                Date dayCursor = monthIndex.newDate(day);
                while (Double.isNaN(dayScore)) {
                    Date yesterday = monthIndex.yesterday(dayCursor);
                    if (firstExpense.getDate().after(yesterday))
                        break; // exit if we passed first date
                    Double yesterDayScore = dayScores.get(yesterday);
                    if (yesterDayScore != null) {
                        dayScore = yesterDayScore;
                        break;
                    } else
                        dayCursor = yesterday;
                }

                // stocke ce score pour ce jour
                dayScores.put(day, dayScore);
                //System.out.println(" " + day + " > " + dayScore);

                // calcule stat mensuelle
                if (dayScore < 0) {
                    nNegatif++;
                }
            }
            // stocke nb jour negatif
            stats.put(MonthStat.NUMBER_OF_NEGATIVE_DAYS, nNegatif);

            // calcul le solde en fin de mois
            if (day != null) {
                Date lastMonthDayCursor = monthIndex.newDate(day);
                List<Expense> lastMonthDayExpenses = getExpenses(expenses, day);
                
                // seek the latest date of the month haaving a solde
                while (lastMonthDayExpenses.size() == 0) { // search earlier if
                    Date yesterday = monthIndex.yesterday(lastMonthDayCursor);
                    if (firstExpense.getDate().after(yesterday))
                        break; // exit if we passed first date
                    lastMonthDayExpenses = getExpenses(expenses, yesterday);
                    lastMonthDayCursor = yesterday;
                }
                if (lastMonthDayExpenses.size() > 0) {
                    double soldeMensuel = lastMonthDayExpenses.get(0).getSolde();
                    stats.put(MonthStat.SOLDE, soldeMensuel);
                }
            }

            monthStatistics.put(month, stats);
        }
        return monthStatistics;
    }

    public List<Expense> getExpenses(Collection<Expense> expenses, Date day) {
        List<Expense> filtered = new ArrayList<Expense>();
        for (Expense e : expenses) {
            if (day.equals(e.getDate())) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    public double computeDayScore(Collection<Expense> expenses) {
        int np = 0;
        int nn = 0;

        for (Expense e : expenses) {
            if (e.getSolde() > 0)
                np++;
            if (e.getSolde() < 0)
                nn++;
        }

        if (np == nn) {
            if (np == 0)
                return Double.NaN;
            else
                return 0;
        } else if (np > nn) {
            return 1;
        } else if (np < nn) {
            return -1;
        } else
            throw new RuntimeException();
    }
}
