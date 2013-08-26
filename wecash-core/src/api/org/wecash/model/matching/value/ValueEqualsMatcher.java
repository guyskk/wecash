package org.wecash.model.matching.value;

import org.wecash.model.Expense;
import org.wecash.model.matching.AbstractExpenseMatcher;

public class ValueEqualsMatcher extends AbstractExpenseMatcher implements IValueMatcher{
    protected double expected;
    
    public ValueEqualsMatcher(double expected) {
        this.expected = expected;
        this.priority = 0;
    }
    
    public ValueEqualsMatcher(double expected, int priority) {
        this.expected = expected;
        this.priority = priority;
    }

    @Override
    public boolean accepts(Expense e) {
        if(e.getValue()==expected)
            return true;
        else
            return false;
    }
    
    @Override
    public double getValue(){
        return expected;
    }
    
    public String toString(){
        return "value-matcher:" + getValue() + " [priority:" + priority + "]";
    }

}
