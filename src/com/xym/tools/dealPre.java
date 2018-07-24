package com.xym.tools;

import java.util.ArrayList;
import java.util.List;

import com.xym.model.myApi;
import com.xym.model.myMashup;

/**
 * @author admin
 *数据的预处理
 *处理词的类型变化还原成原型//没处理
 *tag中近义词统一//meichuli 
 *处理有没有在mashup存在api的，但是apis中不存在
 *处理有没有重复的api或者mashup
 */
public class dealPre extends R_parent{

	private  List<myApi> newapis=new ArrayList<>();
	private List<myMashup> newmashup=new ArrayList<>();
	
	public dealPre(List<myMashup> mashuplist,List<myApi> apilist){
		super();
		super.mashupList=mashuplist;
		super.apiList=apilist;
		dealNullapi();
	}
	
	
	/**
	 * @return
	 * 返回处理好的api的集合
	 */
	public List<myApi> getNewApi(){
		return this.newapis;
	}
	
	/**
	 * @return
	 * 返回处理好的mashup集合
	 */
	public List<myMashup> getNewMahsup(){
		return this.newmashup;
	}
	
	
	
	/**
	 * 处理有没有重复的api或者mashup
	 * 暂时没有处理，只是看一下有没有重复的
	 * 重复：同mashup的api有没有重复，tag有没有重复；同api，tag有没有重复
	 */
	public String dealRepeat(){
		String str="";
		
		boolean is_api= littletools.Isrepeat_api(this.apiList);
		if(is_api==true){//存在就处理
			str="api数据中存在重复现象，";
		}else{
			str="api数据正常，";
		}
		
		int is_mashup=littletools.Isrepeat_mashup(this.mashupList);
	
		switch(is_mashup){
		
		case 0:
			str=str+"mashup正常。";
			break;
		case 1:
			str=str+"mashup存在api重复。";
			
			break;
		case 2:
			str=str+"mashup存在tags重复。";
			
			break;
		case 3:
			str=str+"mashup存在api与tags重复。";
			
			break;
		}
		
		return str;
	}
	
	
	/**
	 * 解决有mashup中有api名称，但是api集合中没有对应api集合的
	 * 如果有的话，就给删除了
	 */
	private void dealNullapi(){
		//获取没有的集合
		List<String>  noTags_api=new ArrayList<>();
		noTags_api =getNoTagsapi(super.mashupList);
		//从mashup中去掉
		List<myMashup> newmashuplist=removeNptagsapi(noTags_api);
		
		this.newapis=super.apiList;
		this.newmashup=newmashuplist;
	}
	
	
	/**
	 * @return
	 * 获取mashup中不存在集合的api
	 */
	private List<String> getNoTagsapi(List<myMashup> mashuplist){
		List<String>  noTags_api=new ArrayList<>();
		List<myMashup> mashuplist_curr=mashuplist;
		
		
		for(int i=0;i<mashuplist_curr.size();i++){
			
			for(int j=0;j<mashuplist_curr.get(i).getmApis().size();j++){
			
				if(getTagsByApi(mashuplist_curr.get(i).getmApis().get(j)) == null){
					noTags_api.add(mashuplist_curr.get(i).getmApis().get(j));
				}
				
			}
		}
		
		if(noTags_api != null){//如果有，就xiaochu chongfu
			List<String>  noTags_api_curr=new ArrayList<>();
			for(int i=0;i<noTags_api.size();i++){
				if(noTags_api_curr == null){
				}else{
					
					boolean istrue=false;
					for(int j=0;j<noTags_api_curr.size();j++){
						if(noTags_api.get(i).equals(noTags_api_curr.get(j))){
							istrue=true;
							break;
						}
					}
					
					if(istrue==false){
						noTags_api_curr.add(noTags_api.get(i));
					}
					
				}
			}
			noTags_api=noTags_api_curr;
		}

		return noTags_api;
	}
	
	
	private List<myMashup> removeNptagsapi(List<String>  noTags_api){
		List<myMashup> mashup_curr_in=super.mashupList;
		List<String> noTags_api_curr=noTags_api;
		List<myMashup> mashup_curr_out=new ArrayList<>();
		
		
		for(int i=0;i<mashup_curr_in.size();i++){
			myMashup m=new myMashup();
			m.setm(mashup_curr_in.get(i).getm().trim());
			m.setmTags(mashup_curr_in.get(i).getmTags());
			m.setmApis(getmApis(mashup_curr_in.get(i).getmApis(),noTags_api_curr));
			mashup_curr_out.add(m);
		}
		
		
		return mashup_curr_out;

	}
	
	
	/**
	 * @param apis
	 * @param notagsapi
	 * @return
	 * 传入需要筛选的集合和筛选集合，获取筛选后的集合，用于消除没有集合的空api名称
	 */
	private List<String> getmApis(List<String> apis,List<String> notagsapi){
		List<String> oldapis=apis;
		List<String> newapis=new ArrayList<>();
		List<String> notagsapi_curr=notagsapi;
		boolean isTrue=false;
		
		for(int i=0;i<oldapis.size();i++){
			
			for(int j=0;j<notagsapi_curr.size();j++){
				if(oldapis.get(i).equals(notagsapi_curr.get(j))){
					isTrue=true;
					break;
				}
			}
			
			if(isTrue == false){
				newapis.add(oldapis.get(i));
			}else{
				isTrue=false;
			}
			
		}
		
		return newapis;
		
		
	}
	
}
