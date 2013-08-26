package org.wecash.demo;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.wecash.analysis.ExpenseFilter;
import org.wecash.analysis.FilterCriteria;
import org.wecash.io.accounts.AbstractAccountReader;
import org.wecash.io.accounts.CAAccountReader;
import org.wecash.io.accounts.ExpenseWriter;
import org.wecash.io.model.ModelReader;
import org.wecash.model.Category;
import org.wecash.model.Expense;
import org.wecash.model.compare.ExpenseDateComparator;
import org.wecash.utils.DateUtils;

public class ExportExpenseInCategory {
    public static String input = "data/input/";
    public static String model = "data/model/";
    public static String output = "data/output/";

    public static void main(String[] args) throws InvalidFormatException, IOException, ParseException {
        // files
        String owner = input + "martin.xlsx";
        String rules = model + "model.txt";
        String filtr = output + "martin-filter.csv";

        // Set filter criteria
        Date datFrom = DateUtils.newDate(2012, 10, 01);
        Date datTo = DateUtils.newDate(2013, 02, 25);
        Set<String> catFilter = new HashSet<String>();
        catFilter.add("home/food");
        FilterCriteria c = new FilterCriteria(catFilter, datFrom, datTo);

        // Load files
        AbstractAccountReader reader = new CAAccountReader();
        List<Expense> expenses = reader.read(new File(owner));
        ModelReader cr = new ModelReader();
        Set<Category> categories = cr.read(rules);

        // apply category model
        ExpenseFilter filter = new ExpenseFilter(categories);
        List<Expense> filtered = filter.apply(expenses, c);
        Collections.sort(filtered, new ExpenseDateComparator());
        
        // export
        ExpenseWriter ew = new ExpenseWriter();
        ew.write(filtered, filtr);
    }
}
