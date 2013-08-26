package org.wecash.io.model;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jzy3d.io.FileReader;
import org.wecash.model.Category;
import org.wecash.model.matching.value.ValueEqualsMatcher;

public class ModelReader implements IParser{
    protected static Pattern categoryPattern = Pattern.compile(space_or_not + "category:(" + path + ")");
    protected static Pattern valueFilterPattern = Pattern.compile(space_or_not + "value:(" + number + ")");
    protected static Pattern labelFilterPattern = Pattern.compile(space_or_not + "label:(" + label + ")");
    protected static Pattern labelFilterPriorityPattern = Pattern.compile(space_or_not + "label:(" + label + ")," + space_or_not + "priority:(" + number+ ")");
    protected static Pattern tagPattern = Pattern.compile(space_or_not + "tag:(" + label + ")" + space_or_not);

    
    public Set<Category> read(String file) throws IOException {
        Set<Category> categories = new TreeSet<Category>();
        List<String> lines = FileReader.read(file);

        Category category = null;
        
        for (String line : lines) {
            if(line.startsWith("//"))
                continue;
            boolean matched = false;
            
            Matcher matchCategory = categoryPattern.matcher(line);
            if(matchCategory.matches()){
                matched = true;
                
                // append an already build category
                if(category!=null){
                    addCategory(categories, category);
                }
                
                // initialize a new category
                String strCategory = matchCategory.group(1);
                category = new Category(strCategory);
            }
            
            Matcher matchValue = valueFilterPattern.matcher(line);
            if(matchValue.matches()){
                matched = true;
                String value = matchValue.group(1);
                category.addExpenseMatcher(new ValueEqualsMatcher(Double.parseDouble(value)));
            }
            
            Matcher matchLabel = labelFilterPattern.matcher(line);
            if(matchLabel.matches()){
                matched = true;
                String label = matchLabel.group(1);
                String regexp = ".*" + label.replace(".", "\\.") + ".*";
                category.addLabelMatcher(regexp, 0);
            }

            Matcher matchLabelPriority = labelFilterPriorityPattern.matcher(line);
            if(matchLabelPriority.matches()){
                matched = true;
                String label = matchLabelPriority.group(1);
                String priority = matchLabelPriority.group(2);
                String regexp = ".*" + label.replace(".", "\\.") + ".*";
                category.addLabelMatcher(regexp, Integer.parseInt(priority));
            }
            
            Matcher matchTag = tagPattern.matcher(line);
            if(matchTag.matches()){
                matched = true;
                String tag = matchTag.group(1);
                category.addTagMatcher(tag);
            }
            
            if(!matched && !"".equals(line)){
                System.err.println("did not matched:" + line);
            }
        }
        // Add last category
        addCategory(categories, category);
        
        return categories;
    }

    private void addCategory(Set<Category> categories, Category category) {
        categories.add(category);
        computeParents(category, categories);
        //category.console();
    }
    
    protected void computeParents(Category category, Collection<Category> categories){
     // add any possible parent
        StringBuilder fullPath = new StringBuilder();
        int n = category.getSegments().length;
        for(int i=0; i<n-1;i++){
            String pathSegment = category.getSegments()[i];
            fullPath.append(pathSegment);
            Category parent = new Category(fullPath.toString());
            categories.add(parent);
            fullPath.append("/");
        }
    }
    
    protected void createAndCategoryWithParents(Set<Category> categories, String path, List<String> patterns, List<String> values) {
        // create category 
        Category category = new Category(path);
        for (String p : patterns)
            category.addLabelMatcher(".*" + p + ".*");
        for(String p: values){
            category.addExpenseMatcher(new ValueEqualsMatcher(Double.parseDouble(p)));
        }
        
        // add category if not exist by path
        categories.add(category);
                
        // add any possible parent
        StringBuilder fullPath = new StringBuilder();
        int n = category.getSegments().length;
        for(int i=0; i<n-1;i++){
            String pathSegment = category.getSegments()[i];
            fullPath.append(pathSegment);
            Category parent = new Category(fullPath.toString());
            categories.add(parent);
            fullPath.append("/");
        }
    }
}
