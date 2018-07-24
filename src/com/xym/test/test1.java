package com.xym.test;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.xym.model.myRule_two;




public class test1 {

	public static void main(String[] args) {	
		
		for(int i=0;i<10;i++){
			if(i==2){
				System.out.println("1："+i);
			}else if(i==4){
				System.out.println("2："+i);
			}else{
				System.out.println("else："+i);
			}
		}
		
//		System.out.println("开始测试");
//		List<myRule_two> rules=new ArrayList<>();
//		
//		myRule_two tworul;
//		System.out.println("装填two数据");
//		for(int i=0;i<1000000;i++){
//			tworul=new myRule_two();
//			tworul.setF(""+i);
//			tworul.setT1(""+i);
//			tworul.setZC(1.000);
//			tworul.setZX(1.00000);
//			tworul.setCount(i);
//			
//			rules.add(tworul);
//			if(i%5 == 0){
//				System.out.println("已装填进度："+i);
//			}
//		}
//		
//		System.out.println("装填结束，共装填数据："+rules.size());
//		boolean isok= myWriteExcel.write_11(rules,"C:/Users/admin/Desktop/", "测试", getMenuAndItems.getTitles_11());
//		if(isok){
//			System.out.println("写入完成");
//		}else{
//			System.out.println("写入失败");
//		}
		
		
		
		
//		test1 t=new test1();
//		t.write_11(rules, "C:/Users/admin/Desktop/", "测试", getMenuAndItems.getTitles_11());
//		
//		int sumcount=rules.size();
//		int sheetcount=(sumcount/5);
//		if(sumcount % 5 ==0){
//		}else{
//			sheetcount++;
//		}
//		
//		for(int j=0;j<sheetcount;j++){
//			int starti,endi;//这个是给循环用的
//	         if(j == sheetcount-1){//如果是最后一个
//	        	 starti=j*5+1;
//	        	 endi=62+1;
//	         }else{
//	        	 starti=j*5+1;
//	        	 endi=starti+5;
//	         }
//	         
//	         System.out.println("start:"+starti+",endi:"+endi);
//	         
//	         for(int i=starti;i<endi;i++){//
//	             System.out.println(i-j*5+">>"+i);
//	         }
//		}
	}
	
	
	@SuppressWarnings("finally")
	public boolean write_11(List<myRule_two> rules,String filePath,String fileName,String[] titles){
		boolean IsOK=false;
		//System.out.println("write_11获取关联规则条数："+rules.size());
		
		try{
			File file=new File(filePath+fileName+".xls");
			file.createNewFile();
	        //由于2003的excel每个sheet最多支持65536行数据，所以采用多分sheet的形式
	        //2:创建工作簿
	         //WritableWorkbook workbook=Workbook.createWorkbook(file);
			WritableWorkbook workbook=Workbook.createWorkbook(file);
			
			for(int j=0;j<=2;j++){
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
		         if(j == 10-1){//如果是最后一个
		        	 starti=j*10+1;
		        	 endi=rules.size()+1;
		         }else{
		        	 starti=j*10+1;
		        	 endi=starti+10;
		         }
		         
		         
		         for(int i=starti;i<endi;i++){//
		             
		             label=new Label(0,i-j*10,"i"+"=>");
		             sheet.addCell(label);
		             //((double)Math.round(rules.get(i-1).get_ZC()*100000)/100000)
		             label=new Label(1,i-j*10,""+i);
		             sheet.addCell(label);
		             //((double)Math.round(rules.get(i-1).get_ZX()*100000)/100000))
		             label=new Label(2,i-j*10,""+i);
		             sheet.addCell(label);
		             
		         }
				
				
				
			}
			   //写入数据，一定记得写入数据，不然你都开始怀疑世界了，excel里面啥都没有
	         workbook.write();
	         System.gc();
		        //最后一步，关闭工作簿
	         workbook.close();
		         
	         System.out.println("已写入第二个"+fileName);
	         IsOK=true;
		}catch(Exception e){
			System.out.println("第二个未写入成功"+fileName+"  >>"+e.getStackTrace().toString());
		}finally{
			return IsOK;
		}
		         
	}
	
}
