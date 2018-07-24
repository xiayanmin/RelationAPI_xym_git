package com.xym.tools;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.xym.model.myApi;
import com.xym.model.myMashup;
import com.xym.model.myRule_three;
import com.xym.model.myRule_two_6;

/**
 * @author admin
 *用于输出结果道excel的
 */
public class myWriteExcel {

	
	private static final int sheetrow=65000;//规定每一个sheet的行数
	private static final double CV_ZC_14=0,CV_ZX_14=0;//write14中使用到支持度和置信度阙值，大于等于才会被输出
	private static final double CV_ZC_11=0,CV_ZX_11=0;//write11中使用到支持度和置信度阙值，大于等于才会被输出
	//
	@SuppressWarnings("finally")
	public static boolean write_0_api(List<myApi> newapi,String filePath,String fileName,String[] titles){
		boolean IsOK=false;
		try{
			File file=new File(filePath+fileName+".xls");
	        file.createNewFile();
	         
	         //2:创建工作簿
	         WritableWorkbook workbook=Workbook.createWorkbook(file);
	         //3:创建sheet,设置第二三四..个sheet，依次类推即可
	         WritableSheet sheet=workbook.createSheet(fileName, 0);
	         //4：设置title,
	         //5:单元格
	         Label label=null;
	         //6:给第一行设置列名
	         for(int i=0;i<titles.length;i++){
	             //x,y,第一行的列名
	             label=new Label(i,0,titles[i]);
	             //7：添加单元格
	             sheet.addCell(label);
	         }

	         for(int i=1;i<newapi.size()+1;i++){
	             
	             label=new Label(0,i,newapi.get(i-1).geta());
	             sheet.addCell(label);
	             
	             label=new Label(1,i,littletools.listToStr(newapi.get(i-1).getaTags()));
	             sheet.addCell(label);    
	         }
	         
	         //写入数据，一定记得写入数据，不然你都开始怀疑世界了，excel里面啥都没有
	         workbook.write();
	        //最后一步，关闭工作簿
	         workbook.close();
	         System.out.println("已写入"+fileName);
	         IsOK=true;
		}catch(Exception e){
			System.out.println("未写入成功"+fileName+"  >>"+e.getStackTrace());
		}finally{
			return IsOK;
		}
		
	}

	@SuppressWarnings("finally")
	public static boolean write_0_mashup(List<myMashup> newmashup,String filePath,String fileName,String[] titles){
		boolean IsOK=false;
		
		try{
			File file=new File(filePath+fileName+".xls");
	        file.createNewFile();
	         
	         //2:创建工作簿
	         WritableWorkbook workbook=Workbook.createWorkbook(file);
	         //3:创建sheet,设置第二三四..个sheet，依次类推即可
	         WritableSheet sheet=workbook.createSheet(fileName, 0);
	         //4：设置title,
	         //5:单元格
	         Label label=null;
	         //6:给第一行设置列名
	         for(int i=0;i<titles.length;i++){
	             //x,y,第一行的列名
	             label=new Label(i,0,titles[i]);
	             //7：添加单元格
	             sheet.addCell(label);
	         }

	         for(int i=1;i<newmashup.size()+1;i++){
	        	    
        		 label=new Label(0,i,newmashup.get(i-1).getm());
	             sheet.addCell(label);
	             
	             label=new Label(1,i,littletools.listToStr(newmashup.get(i-1).getmTags()));
	             sheet.addCell(label);
	             
	             label=new Label(2,i,littletools.listToStr(newmashup.get(i-1).getmApis()));
	             sheet.addCell(label);
	         }
	         
	         //写入数据，一定记得写入数据，不然你都开始怀疑世界了，excel里面啥都没有
	         workbook.write();
	        //最后一步，关闭工作簿
	         workbook.close();
	         System.out.println("已写入"+fileName);
	         IsOK=true;
		}catch(Exception e){
			System.out.println("未写入成功"+fileName+"  >>"+e.getStackTrace());
		}finally{
			return IsOK;
		}
	}
	
	/**
	 * @param rules
	 * @param filePath
	 * @param fileName
	 * @param titles
	 * 
	 * api之间的规则
	 * 输出的时候按照65000个一个shet
	 *  设置有阙值   cutoff value  CV_ZC_11支持度阙值  CV_ZX_11置信度阙值
	 */
	@SuppressWarnings("finally")
	public static boolean write_11(List<myRule_two_6> rules,String filePath,String fileName,String[] titles){
		boolean IsOK=false;
		System.out.println("write_11获取关联规则条数："+rules.size());
		
		int sumcount=rules.size();
		int sheetcount=(sumcount/sheetrow);
		if(sumcount % sheetrow ==0){
		}else{
			sheetcount++;
		}
		
		System.out.println("write_11中sheet条数："+sheetcount);
		try{
			File file=new File(filePath+fileName+".xls");
	        file.createNewFile();
	        //由于2003的excel每个sheet最多支持65536行数据，所以采用多分sheet的形式
	        //2:创建工作簿
	         WritableWorkbook workbook=Workbook.createWorkbook(file);
	        
	         for(int j=0;j<sheetcount;j++){
		         //3:创建sheet,设置第二三四..个sheet，依次类推即可
	        	 WritableSheet sheet=workbook.createSheet(fileName+j, j);
		         //4：设置title,
		         //5:单元格
		         Label label=null;
		         //6:给第一行设置列名
		         for(int i=0;i<titles.length;i++){
		             //x,y,第一行的列名
		             label=new Label(i,0,titles[i]);
		             //7：添加单元格
		             sheet.addCell(label);
		         }
		         
		         int starti,endi;//这个是给循环用的
		         if(j == sheetcount-1){//如果是最后一个
		        	 starti=j*sheetrow+1;
		        	 endi=rules.size()+1;
		         }else{
		        	 starti=j*sheetrow+1;
		        	 endi=starti+sheetrow;
		         }
		         
		         for(int i=starti;i<endi;i++){//
		        	 if(rules.get(i-1).getZC()>=CV_ZC_11 &&  rules.get(i-1).getZX()>=CV_ZX_11){//设置阙值满足一个条件就不打印
		        	
		        		  label=new Label(0,i-j*sheetrow,rules.get(i-1).getF()+"=>"+rules.get(i-1).getT());
				          sheet.addCell(label);
				             //((double)Math.round(rules.get(i-1).get_ZC()*100000)/100000)
				          label=new Label(1,i-j*sheetrow,""+((double)Math.round(rules.get(i-1).getZC()*100000)/100000));
				          sheet.addCell(label);
				             //((double)Math.round(rules.get(i-1).get_ZX()*100000)/100000))
				          label=new Label(2,i-j*sheetrow,""+((double)Math.round(rules.get(i-1).getZX()*100000)/100000));
				          sheet.addCell(label);     
		        	 }   
		         }
		         
		       
	        }    
	         //写入数据，一定记得写入数据，不然你都开始怀疑世界了，excel里面啥都没有
	         workbook.write();
		        //最后一步，关闭工作簿
	         workbook.close();
	         System.out.println("已写入"+fileName);
	         IsOK=true;
		}catch(Exception e){
			System.out.println("未写入成功"+fileName+"  >>"+e.getStackTrace().toString());
		}finally{
			return IsOK;
		}
		         
	}
	
	/**
	 * @param rules
	 * @param filePath
	 * @param fileName
	 * @param titles
	 * 
	 * api之间的规则
	 * 二对一之间的规则
	 * 设置有阙值   cutoff value  CV_ZC_14支持度阙值  CV_ZX_14置信度阙值
	 */
	@SuppressWarnings("finally")
	public static boolean write_14(List<myRule_three> rules,String filePath,String fileName,String[] titles){
		boolean IsOK=false;
		System.out.println("write_14获取关联规则条数："+rules.size());
		int sumcount=rules.size();
		int sheetcount=(sumcount/sheetrow);
		if(sumcount % sheetrow ==0){
		}else{
			sheetcount++;
		}
		
		try{
			File file=new File(filePath+fileName+".xls");
	        file.createNewFile();
	        WritableWorkbook workbook=Workbook.createWorkbook(file);
	         //2:创建工作簿
	        //由于2003的excel每个sheet最多支持65536行数据，所以采用多分sheet的形式
	        for(int j=0;j<sheetcount;j++){
	        	 //3:创建sheet,设置第二三四..个sheet，依次类推即可
		         WritableSheet sheet=workbook.createSheet(fileName+j, j);
		         //4：设置title,
		         //5:单元格
		         Label label=null;
		         //6:给第一行设置列名
		         for(int i=0;i<titles.length;i++){
		             //x,y,第一行的列名
		             label=new Label(i,0,titles[i]);
		             //7：添加单元格
		             sheet.addCell(label);
		         }
	        	
		         int starti,endi;//这个是给循环用的
		         if(j == sheetcount-1){//如果是最后一个
		        	 starti=j*sheetrow+1;
		        	 endi=rules.size()+1;
		         }else{
		        	 starti=j*sheetrow+1;
		        	 endi=starti+sheetrow;
		         }
		         
		         for(int i=starti;i<endi;i++){	
		        	 
		        	 if(rules.get(i-1).getZC() >= CV_ZC_14 && rules.get(i-1).getZX() >= CV_ZX_14){//设置阙值
		        		 
		        		 label=new Label(0,i-j*sheetrow,"("+rules.get(i-1).getF()+","+rules.get(i-1).getT()+")=>"+rules.get(i-1).getT2());
			             sheet.addCell(label);
			             //((double)Math.round(rules.get(i-1).get_ZC()*100000)/100000)
			             //label=new Label(1,i,""+((double)Math.round(rules.get(i-1).getZC()*100000)/100000));
			             label=new Label(1,i-j*sheetrow,""+rules.get(i-1).getZC());
			             sheet.addCell(label);
			             //((double)Math.round(rules.get(i-1).get_ZX()*100000)/100000))
			             //label=new Label(2,i,""+((double)Math.round(rules.get(i-1).getZX()*100000)/100000));
			             label=new Label(2,i-j*sheetrow,""+rules.get(i-1).getZX());
			             sheet.addCell(label);
			             
		        		 
		        	 }
		         }
	        }
	         //写入数据，一定记得写入数据，不然你都开始怀疑世界了，excel里面啥都没有
	         workbook.write();
	        //最后一步，关闭工作簿
	         workbook.close();
	         System.out.println("已写入"+fileName);
	         IsOK=true;
		}catch(Exception e){
			System.out.println("未写入成功"+fileName+"  >>"+e.getStackTrace());
		}finally{
			return IsOK;
		}
		         
	}
	
	
	
}
