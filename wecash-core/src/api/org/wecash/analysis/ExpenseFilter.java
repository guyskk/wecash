package org.wecash.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.wecash.index.CategoryIndex;
import org.wecash.model.Category;
import org.wecash.model.Expense;
import org.wecash.model.matching.date.DateMatcher;

public class ExpenseFilter {
    Set<Category> model;
    
    public ExpenseFilter(Set<Category> model) {
        this.model = model;
    }

    public List<Expense> apply(List<Expense> expenses, FilterCriteria filter) {
        // filter on date
        DateMatcher m = new DateMatcher(filter.from, filter.to);
        Collection<Expense> inRange = m.accepts(expenses);
        
        // Load and build category index
        CategoryIndex categoryIndex = new CategoryIndex(model);
        categoryIndex.index(inRange);
        
        List<Expense> out = new ArrayList<Expense>();
        
        for(Category category: categoryIndex.getCategories()){
            overFilterCategories:
            for(String c: filter.categories){
                if(category.getPath().equals(c)){
                    out.addAll(categoryIndex.getValues(category));
                    break overFilterCategories;
                }
            }
        }
        return out;
    }
}
