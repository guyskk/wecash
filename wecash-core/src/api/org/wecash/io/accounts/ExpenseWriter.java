package org.wecash.io.accounts;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.wecash.model.Expense;
import org.wecash.utils.DateUtils;

import au.com.bytecode.opencsv.CSVWriter;

public class ExpenseWriter {
    protected String dateFormat="yyyy/MM/dd";
    protected NumberFormat valueFormat = NumberFormat.getInstance(Locale.FRENCH);
    
    public ExpenseWriter(){
    }
    
    public ExpenseWriter(String format){
        this.dateFormat = format;
    }
    
    public void write(Collection<Expense> expenses, String file) throws IOException{
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        for(Expense e: expenses)
            writer.writeNext(toStringArray(e));
        writer.close();
    }
    
    public String[] toStringArray(Expense e){
        String[] se = new String[3];
        se[0] = DateUtils.dat2str(e.getDate(), dateFormat);
        se[1] = e.getLabel();
        se[2] = valueFormat.format(e.getValue());
        return se;
    }
    
    public String[] toStringArray(Date d){
        return toStringArray(DateUtils.dat2str(d, dateFormat));
    }
    
    public String[] toStringArray(String s){
        String[] se = new String[1];
        se[0] = s;
        return se;
    }
}
