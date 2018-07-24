package com.xym.tools;

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

import com.xym.enumAndsome.myEnum.excelType;
import com.xym.model.myApi;
import com.xym.model.myMashup;


/**
 * @author admin
 *用来读取原始的excel中的数据，转成list给其他使用
 */
public class myReadExcel {

	private final int start_N=1;//开始的数值，由于数据有列名
	private final int end_N_mashup=3;//mashup结束的位置
	private final int end_N_api=2;//api结束的位置
	
	//转化为自身的两个list
	public List<myMashup> mashupList=new ArrayList<>();
	public List<myApi> apiList=new ArrayList<>();
	
	// 去读Excel的方法readExcel，该方法的入口参数为一个File对象  ,读取mashup
    public void readExcel(File file,excelType mytype) {  
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
                
                addTolist(sheet,mytype);
                
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (BiffException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
	
    
    /**
     * @param sheet
     * @param mytype
     * 将表格内容输入list，分为api与mashup
     */
    private void addTolist(Sheet sheet,excelType mytype){
    	
    	switch(mytype){
    	case mashupType:
    	       myMashup mashupinfo;  
               for (int i = start_N; i < sheet.getRows(); i++) {  
               	mashupinfo=new myMashup();
                   for (int j = 0; j < end_N_mashup; j++) {  
                       String cellinfo = sheet.getCell(j, i).getContents();  
                       
                       switch (j){
                       case 0:
                       	mashupinfo.setm(cellinfo);
                       	break;
                       case 1:
                       	mashupinfo.setmTags(StrTolist1(cellinfo,","));
                       	break;
                       case 2:  
                       	mashupinfo.setmApis(StrTolist1(cellinfo,","));
                       	break;
                       } 
                   }
                   mashupList.add(mashupinfo);
               } 
    		break;
    		
    	case apiType:
    		myApi apiinfo;
    		
    		for (int i = start_N; i < sheet.getRows(); i++) {  
    			apiinfo=new myApi();
                   for (int j = 0; j < end_N_api; j++) {  
                       String cellinfo = sheet.getCell(j, i).getContents();  
                       
                       switch (j){
                       case 0:
                    	   apiinfo.seta(cellinfo.trim());;
                       	break;
                       case 1:
                    	   apiinfo.setaTags(StrTolist1(cellinfo,","));
                       	break;
                       } 
                   }
                   apiList.add(apiinfo);
               } 
    		
    		break;
    			
    	}
    }
    
   
    
    
    /**
     * @param s
     * @param splitstr
     * @return
     * 这里处理带有符号的，分离要素，参数第一个字符串，第二个分离符号
     */
    private List<String> StrTolist1(String s,String splitstr){
    	String[] str=s.split(splitstr);
    	
    	List<String> liststr=new ArrayList<>();
    	for(int i=0;i<str.length;i++){
    		liststr.add(str[i].trim());
    	}
    	return liststr;
    }
}
