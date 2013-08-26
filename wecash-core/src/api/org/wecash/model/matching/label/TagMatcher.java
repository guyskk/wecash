package org.wecash.model.matching.label;

import java.util.regex.Pattern;

import org.wecash.model.Expense;
import org.wecash.model.matching.AbstractExpenseMatcher;

public class TagMatcher extends AbstractExpenseMatcher implements ILabelMatcher{
    protected String label;
    protected Pattern pattern;

    public TagMatcher(String label){
        this.label = label;
    }    
    @Override
    public boolean accepts(Expense e) {
        if(label==null || e.getTag()==null)
            return false;
        else
            return label.equals(e.getTag());
    }

    @Override
    public String getLabel() {
        return label;
    }
    
    public String toString(){
        return "tag-matcher:" + getLabel() + " [priority:" + priority + "]";
    }

}
