package com.xym.model;

import java.util.List;

/**
 * 
 * @author admin
 *
 *mashup的模型类
 *没有完全处理初始化失败的情况
 */

public class myMashup {
	
	private String m="";
	private List<String> mTags;
	private List<String> mApis;
	
	public myMashup(){
		//空初始化函数
	}
	
	public void setm(String mstr){
		this.m=mstr;
	}
	
	public String getm(){
		if(this.m.equals("")){
			return "没初始化";
		}else{
			return this.m;
		}
	}
	
	public void setmTags(List<String> strlist){
		this.mTags=strlist;
	}
	
	public List<String> getmTags(){
		return this.mTags;
	}
	
	public void setmApis(List<String> strlist){
		this.mApis=strlist;
	}
	
	public List<String> getmApis(){
		return this.mApis;
	}
	

}
