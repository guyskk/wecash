package org.wecash.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.wecash.analysis.TreeMatrixAnalysis;
import org.wecash.io.accounts.AbstractAccountReader;
import org.wecash.io.accounts.CAAccountReader;
import org.wecash.io.accounts.ExpenseWriter;
import org.wecash.io.model.ModelReader;
import org.wecash.io.tree.CsvTree;
import org.wecash.model.Category;
import org.wecash.model.Expense;
import org.wecash.model.compare.ExpenseDateComparator;
import org.wecash.model.matching.date.DateMatcher;
import org.wecash.model.treematrix.CategoryDateTreeMatrix;
import org.wecash.model.treematrix.CategoryDateTreeMatrixItem;
import org.wecash.model.treematrix.TreeMatrixLayoutBuilder;
import org.wecash.utils.DateUtils;

import au.com.bytecode.opencsv.CSVWriter;

public class ExportExpenseInCategory2 {
    public static String input = "data/input/";
    public static String model = "data/model/";
    public static String output = "data/output/";

    public static void main(String[] args) throws InvalidFormatException, IOException, ParseException {
        // files
        String owner = input + "martin.xlsx";
        String rules = model + "model.txt";
        String filtr = output + "martin-tree.csv";

        // Set filter criteria
        Date datFrom = DateUtils.newDate(2013, 02, 25);
        Date datTo = DateUtils.newDate(2013, 03, 25);

        // Load files
        AbstractAccountReader reader = new CAAccountReader();
        ModelReader cr = new ModelReader();
        List<Expense> expenses = reader.read(new File(owner));
        Set<Category> categories = cr.read(rules);

        // filter expenses on dates
        DateMatcher m = new DateMatcher(datFrom, datTo);
        Collection<Expense> inRange = m.accepts(expenses);

        /*
         * for(Expense e: inRange){ System.out.println(e); }
         */

        // apply category model
        TreeMatrixAnalysis analysis = new TreeMatrixAnalysis();
        CategoryDateTreeMatrix mat = analysis.analyze(categories, new ArrayList<Expense>(inRange));

        // --------------
        // export
        CsvTree export = new CsvTree();
        export.export(mat, filtr);

        // debug matrix
        TreeMatrixLayoutBuilder builder = new TreeMatrixLayoutBuilder(mat);
        builder.render(new File(filtr.replace(".csv", "-debug.xls")));
    }
}
