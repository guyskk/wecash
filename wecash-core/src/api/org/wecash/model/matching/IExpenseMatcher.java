package org.wecash.model.matching;

import java.util.Collection;

import org.wecash.model.Expense;

public interface IExpenseMatcher {
    public Collection<Expense> accepts(Collection<Expense> expenses);
    public boolean accepts(Expense expense);
    public int getPriority();
}
