package com.xym.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class readtest {

	
	
	private final int start_N=1;//开始的数值，由于数据有列名
	private final int end_N_mashup=3;//mashup结束的位置
	private final int end_N_api=2;//api结束的位置
	
	//转化为自身的两个list
	public  List<String> str1=new ArrayList<>();
	public  List<String> str2=new ArrayList<>();
	// 去读Excel的方法readExcel，该方法的入口参数为一个File对象  ,读取mashup
    public void readExcel(File file) {  
        try {  
            // 创建输入流，读取Excel  
            InputStream is = new FileInputStream(file.getAbsolutePath());  
            // jxl提供的Workbook类  
            Workbook wb = Workbook.getWorkbook(is);  
            // Excel的页签数量  
            int sheet_size = wb.getNumberOfSheets();  
            for (int index = 0; index < sheet_size; index++) {  
                // 每个页签创建一个Sheet对象  
                Sheet sheet = wb.getSheet(index);  
                
                addTolist(sheet);
                
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (BiffException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    
    
    private void addTolist(Sheet sheet){
    	
    	for (int i = start_N; i < sheet.getRows(); i++) {  
               for (int j = 0; j < end_N_api; j++) {  
                   String cellinfo = sheet.getCell(j, i).getContents();  
                   
                   switch (j){
                   case 0:
                	   str1.add(cellinfo);
                   	break;
                   case 1:
                	   str2.add(cellinfo);
                   	break;
                   } 
               }
           } 
    	
    	
    }
}
