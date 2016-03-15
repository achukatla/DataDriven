package com.selenium.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiXlWriter {
	
	public static void writeXl(String path,String sheet,String[][] data){
		System.out.println("path"+path);
		System.out.print(sheet);
		System.out.println("..."+data.length);
		File file = new File(path);
		try {
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet mySheet =  book.createSheet(sheet);
			int rows = data.length;
			int col = data[0].length;
			//System.out.println("rows...."+rows+"col......"+col);
			for(int i =0 ; i<rows ; i++){
				HSSFRow row = mySheet.createRow(i);
				for(int j= 0 ; j<col ; j++){
					HSSFCell cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(data[i][j]);
				}
				
			}
			FileOutputStream os = new FileOutputStream(file);
			book.write(os);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("file cannot be found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in writing the file");
		}
		
	}

}
