package org.wecash.io.accounts;

import java.text.ParseException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.wecash.model.Expense;
import org.wecash.utils.DateUtils;

//http://poi.apache.org/spreadsheet/examples.html
public class CAAccountReader extends AbstractAccountReader {
    private static int COL_YEAR = 6;
    private static int COL_SOLDE = 5;
    private static int COL_TAG = 4;
    private static int COL_CREDIT = 3;
    private static int COL_DEBIT = 2;
    private static int COL_LABEL = 1;
    private static int COL_DATE = 0;

    public Expense readExpense(Row row) throws ParseException {
        // Cell.CELL_TYPE_STRING, Cell.CELL_TYPE_NUMERIC,
        // Cell.CELL_TYPE_FORMULA, Cell.CELL_TYPE_BOOLEAN, Cell.CELL_TYPE_ERROR
        // row.getCell(0).getCellType()
        // if(row.getCell(COL_DATE).getCellType()==Cell.CEL)
        Date date = parseDate(row);
        String label = parseLabel(row);
        
        double debit = 0;
        if(isNumeric(row.getCell(COL_DEBIT)))
            debit = row.getCell(COL_DEBIT).getNumericCellValue();
        
        double credit = 0;
        if(isNumeric(row.getCell(COL_CREDIT)))
            credit = row.getCell(COL_CREDIT).getNumericCellValue();
        
        // tag
        String tag = null;
        if (row.getCell(COL_TAG) != null && row.getCell(COL_TAG).getRichStringCellValue() != null)
            tag = row.getCell(COL_TAG).getRichStringCellValue().getString();

        // solde
        Double solde = null;
        if (row.getCell(COL_SOLDE) != null)
            solde = row.getCell(COL_SOLDE).getNumericCellValue();

        // -------------------
        // set expense
        Expense e = new Expense(date, label, credit - debit);

        if (tag != null && !tag.equals(""))
            e.setTag(tag);
        if (solde != null)
            e.setSolde(solde);

        return e;
    }
    
    protected boolean isNumeric(Cell c){
        if(c!=null && c.getCellType()==Cell.CELL_TYPE_NUMERIC)
            return true;
        else
            return false;
    }

    protected String parseLabel(Row row) {
        return row.getCell(COL_LABEL).getRichStringCellValue().getString().replace('\n', ' ');
    }

    protected Date parseDate(Row row) throws ParseException {
    	String sd = readDate(row);
    	int day = parseDay(sd);
        int month = parseMonth(sd);
        Double year = parseYear(row);
        if (year != null)
            return DateUtils.newDate(year.intValue(), month+1, day);
        else
            return DateUtils.newDate(2013, month+1, day);
    }

	private int parseMonth(String sd) throws ParseException {
		int month = DateUtils.parseMonth3LettersFrench(sd);
        if (month == -1)
            throw new ParseException("can't parse date month '" + sd + "'", 0);
		return month;
	}

	private Double parseYear(Row row) {
		Double year = null;
        if (row.getCell(COL_YEAR) != null)
            year = row.getCell(COL_YEAR).getNumericCellValue();
		return year;
	}

	protected String readDate(Row row) throws ParseException {
		String sd = null;
    	try{
    		sd = row.getCell(COL_DATE).getRichStringCellValue().toString();
    	}catch(Exception e){
    		
    		throw new ParseException("NPE while trying to parse date of row " + row.getRowNum() + ":" + row.getCell(COL_DATE).getStringCellValue(), 0);
    	}
		return sd;
	}

    protected int parseDay(String sd) {
        String num = sd.split("-")[0];
        return Integer.parseInt(num);
    }
}
