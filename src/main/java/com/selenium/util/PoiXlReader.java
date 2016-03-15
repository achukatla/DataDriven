package com.selenium.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;



public class PoiXlReader {


	public static  String[][] read(String path,String mySheet)  {
		// TODO Auto-generated method stub
		
		File file = new File(path);
		FileInputStream is;
		String Xdata[][] = null;
		try {
			is = new FileInputStream(file);
		
		HSSFWorkbook book = new HSSFWorkbook(is);
		HSSFSheet  sheet  = book.getSheet(mySheet);
		Iterator rowIt = sheet.iterator();
		int rows = sheet.getLastRowNum()+1;
		int col = sheet.getRow(0).getLastCellNum();
		Xdata = new String[rows][col];
		for(int i=0; i<rows ; i++){
			HSSFRow row = sheet.getRow(i);
			for(int j=0; j<col ;j++ ){
				HSSFCell  cell = row.getCell(j);
				Xdata[i][j]= "";
				if(cell != null)
					Xdata[i][j] = cellToString(cell);
				//System.out.println("---"+Xdata[i][j]);
			}
		}
		return Xdata ;
		} catch (FileNotFoundException e) {
			System.out.println("filenot found");
		} catch (IOException e) {
			System.out.println("Error in reading the file"+e);
		}
		return Xdata ;
		
		
		}
	public static String cellToString(HSSFCell cell) { 
		// This function will convert an object of type excel cell to a string value
		int type = cell.getCellType();                        
		Object result;                        
		switch (type) {                            
			case HSSFCell.CELL_TYPE_NUMERIC: //0                                
				result = cell.getNumericCellValue();                                
				break;                            
			case HSSFCell.CELL_TYPE_STRING: //1                                
				result = cell.getStringCellValue();                                
				break;                            
			case HSSFCell.CELL_TYPE_FORMULA: //2                                
				throw new RuntimeException("We can't evaluate formulas in Java");  
			case HSSFCell.CELL_TYPE_BLANK: //3                                
				result = "%";                                
				break;                            
			case HSSFCell.CELL_TYPE_BOOLEAN: //4     
				result = cell.getBooleanCellValue();       
				break;                            
			case HSSFCell.CELL_TYPE_ERROR: //5       
				throw new RuntimeException ("This cell has an error");    
			default:                  
				throw new RuntimeException("We don't support this cell type: " + type); 
				}                        
		return result.toString();      
		}
	
	

}
