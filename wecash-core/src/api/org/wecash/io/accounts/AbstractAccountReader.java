package org.wecash.io.accounts;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.wecash.model.Expense;

public abstract class AbstractAccountReader {
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public abstract Expense readExpense(Row row) throws ParseException;

    
    public List<Expense> read(File file) throws InvalidFormatException, IOException, ParseException {
        List<Expense> expenses = new ArrayList<Expense>();

        Workbook wb = WorkbookFactory.create(file);
        Sheet sheet = wb.getSheetAt(0);
        boolean doneHeader = false;
        for (Row row : sheet) {
            if (!doneHeader) {
                doneHeader = true;
            } else {
            	Logger.getLogger(AbstractAccountReader.class).info(file + " " + row.getRowNum());
                Expense e = readExpense(row);
                postParse(e);
                expenses.add(e);
            }
        }
        return expenses;
    }


    protected void postParse(Expense e) {
        if(e.getValue()==0 || Double.isNaN(e.getValue()))
            Logger.getLogger(this.getClass().getSimpleName()).warn("Expense with weird value: " + e);
    }

}
