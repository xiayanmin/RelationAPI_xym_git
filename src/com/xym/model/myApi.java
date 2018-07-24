package com.xym.model;

import java.util.List;


/**
 * 
 * @author admin
 *api模型
 */
public class myApi {

	private String a="";
	private List<String> aTags;
	
	public myApi(){
		//空初始化
	}
	
	public void seta(String astr){
		this.a=astr;
	}
	
	public String geta(){
		if(a.equals("")){
			return "api没有初始化";
		}else{
			return this.a;
		}
	}
	
	public void setaTags(List<String> strlist){
		this.aTags=strlist;
	}
	
	public List<String> getaTags(){
		return this.aTags;
	}
	
}
