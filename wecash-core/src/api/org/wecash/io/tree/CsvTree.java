package org.wecash.io.tree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.wecash.io.accounts.ExpenseWriter;
import org.wecash.model.Expense;
import org.wecash.model.compare.ExpenseDateComparator;
import org.wecash.model.treematrix.CategoryDateTreeMatrix;
import org.wecash.model.treematrix.CategoryDateTreeMatrixItem;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvTree {
    public void export(CategoryDateTreeMatrix mat, String file) throws IOException{
        TreeSet<CategoryDateTreeMatrixItem> t = mat.getTree();
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        exportList(t, writer);
        writer.close();
    }
    
    protected void exportFullTree(TreeSet<CategoryDateTreeMatrixItem> t, CSVWriter writer) {
        // for category
        for(CategoryDateTreeMatrixItem mi: t){
            writer.writeNext(w.toStringArray(mi.getPath()));
            
            // for month
            for(Date d: mi.getColumns()){
                writer.writeNext(w.toStringArray(d));
                
                // for expense
                for(Expense e: mi.getValueFor(d)){
                    writer.writeNext(w.toStringArray(e));
                }
            }
            writer.writeNext(new String[0]);
        }
    }
    
    protected void exportList(TreeSet<CategoryDateTreeMatrixItem> t, CSVWriter writer) {
        for(CategoryDateTreeMatrixItem mi: t){
            writer.writeNext(w.toStringArray(mi.getPath()));
            
            List<Expense> ee = mi.getValues();
            Collections.sort(ee, new ExpenseDateComparator());
            for(Expense e: ee){
                writer.writeNext(w.toStringArray(e));
            }
            writer.writeNext(new String[0]);
        }
    }
    
    protected static ExpenseWriter w = new ExpenseWriter();
}
