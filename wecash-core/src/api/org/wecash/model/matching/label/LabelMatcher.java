package org.wecash.model.matching.label;

import java.util.regex.Pattern;

import org.wecash.model.Expense;
import org.wecash.model.matching.AbstractExpenseMatcher;

public class LabelMatcher extends AbstractExpenseMatcher implements ILabelMatcher{
    protected String label;
    protected Pattern pattern;

    public LabelMatcher(String label){
        this(label, false, 0);
    }
    
    public LabelMatcher(String label, int priority){
        this(label, false, priority);
    }
    
    public LabelMatcher(String label, boolean caseSensitive, int priority){
        this.priority = priority;
        this.label = label;
        if(caseSensitive)
            this.pattern = Pattern.compile(label);
        else
            this.pattern = Pattern.compile(label, Pattern.CASE_INSENSITIVE);
    }
    
    @Override
    public boolean accepts(Expense e) {
        return pattern.matcher(e.getLabel()).matches();
    }

    @Override
    public String getLabel() {
        return label;
    }
    
    public String toString(){
        return "label-matcher:" + getLabel() + " [priority:" + priority + "]";
    }
}
