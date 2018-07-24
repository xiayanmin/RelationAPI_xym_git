package com.xym.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xym.model.myApi;
import com.xym.model.myMashup;

/**
 * @author admin
 *这里处理api-api，mashup，api-mashup的共同部分
 */
public class R_parent {
	//传进来的两个表
	protected  List<myMashup> mashupList=new ArrayList<>();
	protected  List<myApi> apiList=new ArrayList<>();
	
	//修改的将api 的list转为map
	protected  Map<String,List<String>> apimap=new HashMap<>();
	
	public R_parent(){
		//空
	}
	
	//将api的list转为map

	protected void apilist2map(){
		
		for(int i=0;i<this.apiList.size();i++){
			this.apimap.put(this.apiList.get(i).geta(), this.apiList.get(i).getaTags());
		}
	}
	
	
	/**
	 * @param apistr
	 * @return
	 * 根据api的名称获取api的tags，如果不存在就返回null
	 */
	protected List<String> getTagsByApi(String apistr){
	List<String> tags;
	for(int i=0;i<this.apiList.size();i++){
		if(apiList.get(i).geta().equals(apistr)){
			tags=apiList.get(i).getaTags();
			return tags;
		}
	}
		return null;	
	}
	
	
	protected List<String> getTagByApi2(String apistr){
		List<String> tags=this.apimap.get(apistr);
		if(tags==null){
			System.out.println(apistr+"不存在tags");
			return null;
		}else{
			return tags;
		}
	}
	
	/**
	 * @param tag1
	 * @param tag2
	 * @return
	 * 验证两个标签是否相同，如果相同就返回true，不相同就返回false
	 * 验证三个标签是否相同，如果相同就返回true，不相同就返回false
	 */
	protected boolean IsSame(String tag1,String tag2,String tag3){
		
		if(tag3 == null){
			if(tag1.equals(tag2)){
				return true;
			}else{
				return false;
			}
		}else{
			
			if(tag1.equals(tag3)){
				return true;
			}else if(tag2.equals(tag3)){
				return true;
			}else{
				return false;
			}
		}
		
	}
	
}
