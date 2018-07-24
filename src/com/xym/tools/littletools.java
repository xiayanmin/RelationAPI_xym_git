package com.xym.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xym.model.myApi;
import com.xym.model.myMashup;

//import edu.stanford.nlp.simple.Sentence;

/**
 * @author admin
 *放置一些小的公共方法
 *有处理符号分离的
 *有获取打开文件地址的，主要是\/的转化
 *有获取打开文件名称的，主要是\/的转化
 *有检验是否需要预处理的
 */
public class littletools {

	
	 /**
     * @param s
     * @param splitstr
     * @return
     * 这里处理带有符号的，分离要素，参数第一个字符串，第二个分离符号
     */
    public static List<String> StrTolist1(String s,String splitstr){
    	String[] str=s.split(splitstr);
    	
    	List<String> liststr=new ArrayList<>();
    	for(int i=0;i<str.length;i++){
    		liststr.add(str[i]);
    	}
    	return liststr;
    }
    
    /**
     * @param list
     * @param aplitstr
     * @return
     * 将list的string变成一个string
     */
    public static String listToStr(List<String> list){
    	String str="";
    	if(list == null){
    	}else{
    		for(int i=0;i<list.size();i++){
    			if(i==list.size()-1){
        			str=str+list.get(i);
        		}else{
        			str=str+list.get(i)+",";
        		}
        	}
    	}
    	
    	return str;
    	
    }
    
    /**
	 * @return
	 * 获取打开的地址
	 * 用来读取文件
	 */
	public static String getnewFilePath(String str){
		String filepath=str.replace("\\", "/");
		List<String> strlist=littletools.StrTolist1(filepath, "/");
		filepath="";
		filepath=strlist.get(0)+"/";
		
		for(int i=1;i<strlist.size()-1;i++){	
			filepath=filepath+strlist.get(i)+"/";
		}
		
		return filepath;
	}
	
	
	
	/**
	 * @return
	 * 获取打开文件的名字
	 * 用来读取文件
	 */
	public static String getnewFileName(String str){
		String filename=str.replace("\\", "/");
		List<String> strlist=littletools.StrTolist1(filename, "/");
		filename="";
		filename=strlist.get(strlist.size()-1);
		return filename;	
	}
	
	
	/**
	 * @param apis
	 * @return\
	 * 检查api的原始数据是否存在同一个api有重复的tags
	 * 存在返回true
	 */
	public static boolean Isrepeat_api(List<myApi> apis){
		boolean isTrue=false;
		
		for(int i=0;i<apis.size();i++){
			
			for(int j=0;j<apis.get(i).getaTags().size();j++){
				isTrue=Issame(apis.get(i).getaTags(),apis.get(i).getaTags().get(j));
				if(isTrue==true){
					return isTrue;
				}
			}
		}
		
		return isTrue;
	}
	
	/**
	 * @param list
	 * @return
	 * 检查原始的mashup原始数据是否存在一组中相同的api或者一组中相同的tag
	 * 不存在就返回0，api存在相同就返回1，tag存在相同就返回2，都存在就返回3
	 */
	public static int Isrepeat_mashup(List<myMashup> list){
		int c=0;
		boolean isTrue=false;
		
		for(int i=0;i<list.size();i++){
			for(int j=0;j<list.get(i).getmApis().size();j++){
				isTrue=Issame(list.get(i).getmApis(),list.get(i).getmApis().get(j));
				if(isTrue==true){
					c=1;
					break;
				}
			}
			if(c==1){
				break;
			}
		}
		
		isTrue=false;
		for(int i=0;i<list.size();i++){
			
			
			for(int j=0;j<list.get(i).getmTags().size();j++){
				
				isTrue=Issame(list.get(i).getmTags(),list.get(i).getmTags().get(j));
				
				if(isTrue==true){
					if(c==0){
						c=2;
					}else if(c==1){
						c=3;
					}
					return c;
				}
				
			}
		}
		
		return c;
		
	}
	
	
	/**
	 * @return
	 * 查看每一个传进来的tags是否和存在list中有重复的
	 * 有就返回true
	 */
	private static boolean Issame(List<String> list,String tag){
		boolean isTrue=false;
		int c=0;
		
		for(int i=0;i<list.size();i++){
			if(list.get(i).equals(tag)){
				c=c+1;
			}else{
			}
			
			if(c==2){
				isTrue=true;
				return isTrue;
			}
		}
		
		return isTrue;
	}
	
	/**
	 * @param path
	 * @param name
	 * @param text
	 * @return
	 * @throws IOException
	 * 用来保存一个txt文本
	 */
	@SuppressWarnings("finally")
	public static boolean writeTxt(String path,String name,String text) throws IOException{
		boolean Isok=false;
		
		String savefile = path+name+".txt";  
		String str=text;
		FileWriter fwriter = null;  
		
		try {  
		  fwriter = new FileWriter(savefile);  
		  fwriter.write(str); 
		  Isok=true;
		  
		 } catch (IOException ex) {  
		  ex.printStackTrace(); 
		 } finally {    
		   fwriter.flush();  
		   fwriter.close(); 
		   return Isok;
		 }
	}
	
	
	/**
	 * @return
	 * 获取传入单词的原型
	 */
	public String getSaw(String str){
		String outstr="";
		//Sentence sent = new Sentence(str+" s.");
		//outstr=sent.lemma(0);
		return outstr;
	}
}
