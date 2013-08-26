package org.wecash.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.wecash.index.CategoryIndex;
import org.wecash.index.IndexIntersection;
import org.wecash.index.MonthIndex;
import org.wecash.model.Category;
import org.wecash.model.Expense;
import org.wecash.model.treematrix.CategoryDateTreeMatrix;
import org.wecash.model.treematrix.CategoryDateTreeMatrixItem;

public class TreeMatrixAnalysis {
    public CategoryDateTreeMatrix analyze(Set<Category> categories, List<Expense> expenses) throws IOException {
        // Load and build index
        CategoryIndex categoryIndex = new CategoryIndex(categories);
        categoryIndex.index(expenses);

        MonthIndex monthIndex = new MonthIndex();
        monthIndex.index(expenses);

        // Intersect indexes
        IndexIntersection<Category, Date, Expense> intersect = new IndexIntersection<Category, Date, Expense>(categoryIndex, monthIndex);
        intersect.build();
        //intersect.console();

        // Build tree matrix items;
        TreeSet<CategoryDateTreeMatrixItem> tree = new  TreeSet<CategoryDateTreeMatrixItem>();
        for (Category c : intersect.getIndex1().getCategories()) {
            
            Map<Date,Set<Expense>> map = new HashMap<Date,Set<Expense>>();
            for (Date d : intersect.getIndex2().getCategories()) {
                map.put(d, intersect.getIndexedIntersection(c, d));
            }
            CategoryDateTreeMatrixItem item = new CategoryDateTreeMatrixItem(c,map);
            tree.add(item);
        }
        for(Category c: categories){
            // add categories not appearing in index intersection
            CategoryDateTreeMatrixItem item = new CategoryDateTreeMatrixItem(c);
            if(!tree.contains(item)){
                tree.add(item);
            }
        }
        
        // Build tree matrix
        List<Date> columns = new ArrayList<Date>(intersect.getIndex2().getCategories());
        CategoryDateTreeMatrix mat = new CategoryDateTreeMatrix(tree, columns);
        
        return mat;
    }
}
