package org.wecash.model.matching.date;

import java.util.Date;

import org.wecash.model.Expense;
import org.wecash.model.matching.AbstractExpenseMatcher;

public class DateMatcher extends AbstractExpenseMatcher implements IDateMatcher{
    protected Date from;
    protected Date to;
    
    /**
     * 
     * @param from, null is allowed
     * @param to, null is allowed
     */
    public DateMatcher(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Returns true if the input expense satisfies the matcher
     * constraints.
     * 
     * Special cases:
     * <ul>
     * <li>Any undefined date constraint is automatically accepted.
     * <li>Any expense without date is automatically rejected.
     * </ul>
     * 
     * Matcher consider date bounds inclusive. In other word, an expense
     * if accepted if either
     * <ul>
     * <li>e.getDate.equals(getFrom())
     * <li>e.getDate.equals(getTo())
     * </ul>
     */
    @Override
    public boolean accepts(Expense e) {
        if(e.getDate()==null)
            return false;
        
        if(from!=null){
            // not allowed to happen before start date
            if(e.getDate().getTime() < from.getTime()){
                return false;
            }
        }
        if(to!=null){
            // not allowed to happen before start date
            if(e.getDate().getTime() > to.getTime()){
                return false;
            }
        }
        
        // if reach this point, we accept the date
        return true;
    }

    @Override
    public Date getFrom() {
        return from;
    }

    @Override
    public Date getTo() {
        return to;
    }
}
