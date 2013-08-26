package org.wecash.model.matching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.wecash.model.Expense;

public abstract class AbstractExpenseMatcher implements IExpenseMatcher{
    protected int priority;

    public AbstractExpenseMatcher() {
        super();
    }

    @Override
    public int getPriority() {
        return priority;
    }
    
    @Override
    public Collection<Expense> accepts(Collection<Expense> expenses){
        List<Expense> accepted = new ArrayList<Expense>();
        for(Expense expense: expenses){
            if(accepts(expense))
                accepted.add(expense);
        }
        return accepted;
    }
}