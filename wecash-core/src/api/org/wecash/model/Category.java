package org.wecash.model;

import java.util.ArrayList;
import java.util.List;

import org.wecash.model.matching.IExpenseMatcher;
import org.wecash.model.matching.label.LabelMatcher;
import org.wecash.model.matching.label.TagMatcher;
import org.wecash.model.matching.value.ValueEqualsMatcher;

/**
 * A category has a name, a hierarchical path, and a set of
 * matchers.
 * 
 * A Category will return true to accepts(Expense) if any
 * of its IExpenseMatcher match the input expense, either 
 * from a label, a value, a tag
 * 
 * @author Martin
 */
public class Category implements Comparable<Category>{
    public Category(String path) {
        setPath(path);
        setName(getLastSegment());
        initMatchers();
    }

    public Category(String path, String name) {
        setPath(path);
        this.name = name;
        initMatchers();
    }

    private void initMatchers() {
        this.expenseMatchers = new ArrayList<IExpenseMatcher>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        this.segments = path.split("/");
    }
    
    public String[] getSegments(){
        return segments;
    }
    
    public String getLastSegment(){
        return segments[segments.length-1];
    }
    
    /* MATCHERS */
    
    public boolean accepts(Expense e) {
        for(IExpenseMatcher m: expenseMatchers){
            if(m.accepts(e)){
                return true;
            }
        }
        return false;
    }
    
    public int[] getPriorities(){
        int[] priorities = new int[expenseMatchers.size()];
        int k = 0;
        for(IExpenseMatcher m: expenseMatchers){
            priorities[k++] = m.getPriority();
        }
        return priorities;
    }
    
    /* */
    
    public List<IExpenseMatcher> getExpenseMatchers() {
        return expenseMatchers;
    }
    
    public void addExpenseMatcher(IExpenseMatcher matcher){
        expenseMatchers.add(matcher);
    }
    
    /* */
    
    public void addLabelMatcher(String pattern) {
        addLabelMatcher(pattern, false, 0);
    }

    public void addLabelMatcher(String pattern, int priority) {
        addLabelMatcher(pattern, false, priority);
    }

    public void addLabelMatcher(String pattern, boolean caseSensitive, int priority) {
        addExpenseMatcher(new LabelMatcher(pattern, caseSensitive, priority));
    }

    public void addValueMatcher(double value) {
        addExpenseMatcher(new ValueEqualsMatcher(value));
    }
    
    public void addTagMatcher(String tag) {
        addExpenseMatcher(new TagMatcher(tag));
    }
    
    /* */
    
    @Override
    public int compareTo(Category arg0) {
        return path.compareTo(arg0.getPath());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        return true;
    }

    public String toString(){
        return "Category " + path;
    }
    
    public StringBuffer toString(StringBuffer sb){
        sb.append("Category: " + path + "\n");
        for(IExpenseMatcher m: expenseMatchers){
            sb.append(" " + m + "\n");
        }
        return sb;
    }
    
    public void console(){
        System.out.println(toString(new StringBuffer()));
    }
    
    /* */

    protected String name;
    protected String path;
    protected String[] segments;
    
    protected List<IExpenseMatcher> expenseMatchers;
}
