package org.wecash.demo;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jzy3d.utils.LoggerUtils;
import org.wecash.analysis.TreeMatrixAnalysis;
import org.wecash.io.accounts.AbstractAccountReader;
import org.wecash.io.accounts.CAAccountReader;
import org.wecash.io.accounts.SGAccountReader;
import org.wecash.io.model.ModelReader;
import org.wecash.io.tree.CsvTree;
import org.wecash.model.Category;
import org.wecash.model.Expense;
import org.wecash.model.aggregator.MonthStatistics;
import org.wecash.model.aggregator.MonthStatisticsProcessor;
import org.wecash.model.treematrix.CategoryDateTreeMatrix;
import org.wecash.model.treematrix.TreeMatrixLayoutBuilder;

public class ExpenseAnalysis {
    public static String input = "data/input/";
    public static String model = "data/model/";
    public static String output = "data/output/";
    
    public static void main(String[] args) throws InvalidFormatException, IOException, ParseException {
        LoggerUtils.minimal();
        
        String model = "model.txt";
        
        ExpenseAnalysis a = new ExpenseAnalysis();
        a.analyze(new CAAccountReader(), "martin.xls", model, "analysis-martin.xls", "analysis-martin.csv");
        a.analyze(new SGAccountReader(), "family.xls", model, "analysis-family.xls", "analysis-family.csv");
        a.analyze(new SGAccountReader(), "manu.xls", model, "analysis-manu.xls", "analysis-manu.csv");
    }

    public CategoryDateTreeMatrix analyze(AbstractAccountReader reader, String owner, String rules, String xlMatrix, String textTree) throws InvalidFormatException, IOException, ParseException {
        owner = input + owner;
        rules = model + rules;
        xlMatrix = output + xlMatrix;
        textTree = output + textTree;
        
        // Load an account
        List<Expense> expenses = reader.read(new File(owner));
        ModelReader cr = new ModelReader();
        Set<Category> categories = cr.read(rules);

        // Compute tree matrix model
        TreeMatrixAnalysis analysis = new TreeMatrixAnalysis();
        CategoryDateTreeMatrix mat = analysis.analyze(categories, expenses);
        //mat.console();
        
        
        // TODO: fix me
        //Map<Date, MonthStatistics> monthStats = monthStat(expenses);
        
        // Export excel
        TreeMatrixLayoutBuilder builder = new TreeMatrixLayoutBuilder(mat);
        builder.render(new File(xlMatrix));
        
        // Export text
        CsvTree export = new CsvTree();
        export.export(mat, textTree.replace(".csv", "-tree.csv"));

        //FileReader.write(textTree, mat.toString(new ArrayList<String>()));
        return mat;
    }
    
    public Map<Date, MonthStatistics> monthStat(List<Expense> expenses){
        MonthStatisticsProcessor ma = new MonthStatisticsProcessor();
        Map<Date, MonthStatistics> stats = ma.getNegativeDaysPerMonth(expenses);
        return stats;
    }
}
