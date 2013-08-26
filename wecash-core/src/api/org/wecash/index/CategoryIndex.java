package org.wecash.index;

import java.util.HashSet;
import java.util.Set;

import org.wecash.model.Category;
import org.wecash.model.Expense;

// an entry may match several patterns
// TODO: disambiguate
public class CategoryIndex extends AbstractIndex<Category,Expense> implements IIndex<Category,Expense>{
    protected Set<Category> categories;
    protected Category unclassified = new Category("#unclassified");
    protected boolean highestPriorityCategoryOnly = true;
    
    public CategoryIndex(Set<Category> patterns) {
        this.categories = patterns;
    }
    
    @Override
    protected Set<Category> index(Expense e) {
        Set<Category> indices = new HashSet<Category>();
        if(!highestPriorityCategoryOnly){
            for(Category category: categories){
                if(category.accepts(e)){
                    indices.add(category);
                }
            }
            if(indices.size()==0)
                indices.add(unclassified);
        }
        else{
            Category bestCategory = null;
            int bestPriority = Integer.MAX_VALUE;
            for(Category category: categories){
                if(category.accepts(e)){
                    int minMatcherPriority = min(category.getPriorities());
                    if(bestPriority>minMatcherPriority){
                        bestCategory = category;
                    }
                }
            }
            if(bestCategory!=null)
                indices.add(bestCategory);
            if(indices.size()==0)
                indices.add(unclassified);
        }
        return indices;
    }
    
    public static int min(int[] values){
        if(values.length==0)
            throw new IllegalArgumentException("Input array must have a length greater than 0");
        
        int minv = Integer.MAX_VALUE;
        
        for( int i=0; i<values.length; i++ )
            if( values[i] < minv )
                minv = values[i];   
        return minv;
    }
}
