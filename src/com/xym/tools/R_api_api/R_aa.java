package com.xym.tools.R_api_api;

import java.util.ArrayList;
import java.util.List;

import com.xym.model.myApi;
import com.xym.model.myMashup;
import com.xym.model.myRule_two;
import com.xym.tools.R_parent;

/**
 * @author admin
 *处理api-api的关系
 *直接使用方式，初始化之后，调用getCount_before(),getCount_after(),之后再调用getRule()可以完成获取筛选前后标签对以及获取计算后的规则集合
 */
public class R_aa extends R_parent{

	private double oldCount=0;//筛选之前的标签对数值
	private double newCount=0;//筛选之后的标签对数值
	private List<myRule_two> oldRule_list=new ArrayList<>();//筛选之前的标签规则集合
	private List<myRule_two> SXrule_list=new ArrayList<>();//用于筛选的集合
	private List<myRule_two> after_SXRule_list=new ArrayList<>();//筛选后的标签规则集合不含有zc和zx
	private List<myRule_two> newRule_list=new ArrayList<>();//最终的标签规则集合含有支持度和置信度
	
	/**
	 * @param mashuplist
	 * @param apilist
	 * 
	 * 完成初始化，获取两个list
	 */
	public R_aa(List<myMashup> mashuplist,List<myApi> apilist){
		super();
		super.mashupList=mashuplist;
		super.apiList=apilist;
		A();
	}
	
	
	/**
	 * @return
	 * 获取筛选之前
	 * 的标签对数
	 */
	public double getCount_before(){
		
		double count=0;
		for(int i=0;i<this.oldRule_list.size();i++){
			count=count+this.oldRule_list.get(i).getCount();
		}
		this.oldCount=count;
		
		return this.oldCount;
	}
	
	/**
	 * @return
	 *  获取筛选之后
	 *  的标签对数
	 */
	public double getCount_after(){	
		return this.newCount;
	}
	
	/**
	 * @return
	 * 获取关联规则的list
	 */
	public List<myRule_two> getRule(){	
		B();
		
		for(int i=0;i<this.newRule_list.size();i++){
			System.out.println(this.newRule_list.get(i).getF()+"==>>"+this.newRule_list.get(i).getT());
		}
		
		return this.newRule_list;
	}
	
	
	
	
	/**
	 * 控制这个程序的主方法
	 * 先处理不筛选的（）
	 * A,B方法用于后面的快捷处理中直接使用
	 */
	private void A(){
		
		int mashuplistsize=super.mashupList.size();
		int apisize_curr=0;	
		
		//获得没有筛选的集合
		for(int i=0;i<mashuplistsize;i++){//控制mashup
			apisize_curr=mashupList.get(i).getmApis().size();
			for(int j=0;j<apisize_curr-1;j++){//控制api
				for(int m=j+1;m<apisize_curr;m++){
					
					setoldRule_list(
							super.mashupList.get(i).getmApis().get(j), super.mashupList.get(i).getmApis().get(m),i);
				}
			}
		}
		
		//制作筛选的集合
		for(int i=0;i<mashuplistsize;i++){		
			apisize_curr=mashupList.get(i).getmApis().size();		
			for(int j=0;j<apisize_curr;j++){					
				setSXRule_list(this.mashupList.get(i).getmApis().get(j));	
			}	
		}
		
		
		//获得筛选后的集合//恶心，一个bug就在这里出过		
		int oldlist_size=this.oldRule_list.size();		
		for(int i=0;i<oldlist_size;i++){//控制oldlist的顺序																
			if(IsExite(this.SXrule_list, this.oldRule_list.get(i).getF(), this.oldRule_list.get(i).getT())){						
				//在筛选集合中存在就不做处理，不存在就加到新的list中				
			}else{						
				this.after_SXRule_list.add(this.oldRule_list.get(i));			
			}		
		}
		
		//先获取总的标签对，也就是筛选之后的标签对数		
		double count=0;								
		for(int i=0;i<this.after_SXRule_list.size();i++){						
			count=count+this.after_SXRule_list.get(i).getCount();						
		}										
		this.newCount=count;
		
	}
	
	/**
	 * 获取置信度和支持度
	 */
	private void B(){
		//制作最终的集合			
		setnewRule_list();	
	}

	/**
	 * 制作筛选之前的list集合
	 * 先获取对应的api，然后得到对应的tag的集合
	 * 
	 */
	private void setoldRule_list(String api1,String api2,int im){
		List<String> tags1=getTagsByApi(api1);
		List<String> tags2=getTagsByApi(api2);
		if(tags1== null){
			System.out.println("tags1有不存在的:"+api1);
		}
		if(tags2== null){
			System.out.println("tags2有不存在的:"+api2);
		}
		
		
		for(int i=0;i<tags1.size();i++){
			
			for(int j=0;j<tags2.size();j++){
				
				if(IsSame(tags1.get(i),tags2.get(j), null)){//相同的话不处理
				}else{//不相同，如果不存在就加上去，存在就+1
					if(IsExite(this.oldRule_list,tags1.get(i), tags2.get(j))){
						updateOldlist(tags1.get(i), tags2.get(j));
					}else{
						addOldlist(tags1.get(i), tags2.get(j));
					}
				}
			}
		}
	}
	
	
	
	
	
	/**
	 * @param api
	 * 
	 * 制作筛选list集合
	 */
	private void setSXRule_list(String api){
		if(api.equals("")){
			return;
		}else{
			List<String> tags=getTagsByApi(api);
			
			for(int i=0;i<tags.size()-1;i++){
				
				for(int j=i+1;j<tags.size();j++){//是否存在，存在就不做处理，不存在就加上去
					if(IsExite(this.SXrule_list, tags.get(i), tags.get(j))){
					}else{
						myRule_two rule=new myRule_two();
						rule.setF(tags.get(i));
						rule.setT1(tags.get(j));
						this.SXrule_list.add(rule);
					}
				}
			}
		}
		
	
	}
	
	
	/**
	 * 制作最终的集合
	 * 使用after的集合，然后计算支持度和置信度
	 * 由于t1=>t2与t2=>t1的支持度都是一样的，所以先处理支持度
	 */
	private void setnewRule_list(){
		List<myRule_two> list_zc_curr=new ArrayList<>();
		
		int afterlist_size=this.after_SXRule_list.size();
		//先获取总的标签对，也就是筛选之后的标签对数		
		
		//处理支持度
		for(int i=0;i<afterlist_size;i++){
			//this.after_SXRule_list.get(i).setZC(this.after_SXRule_list.get(i).getCount()/this.newCount);
			//this.after_SXRule_list.get(i).setZC(this.after_SXRule_list.get(i).getCount()/super.mashupList.size());..
			this.after_SXRule_list.get(i).setZC(get_ZC_FZ(this.after_SXRule_list.get(i).getF(),this.after_SXRule_list.get(i).getT(),this.after_SXRule_list)/super.mashupList.size());
			System.out.println("count："+this.after_SXRule_list.get(i).getCount());
		}
		
		//处理置信度
		for(int i=0;i<afterlist_size;i++){
			myRule_two rule1=this.after_SXRule_list.get(i);
			
			myRule_two rule2=new myRule_two();
			rule2.setF(this.after_SXRule_list.get(i).getT());
			rule2.setT1(this.after_SXRule_list.get(i).getF());
			rule2.setCount(0);
			rule2.setZC(this.after_SXRule_list.get(i).getZC());
				
			list_zc_curr.add(rule1);
			list_zc_curr.add(rule2);	
		}
		
		for(int i=0;i<list_zc_curr.size();i++){
			
			//list_zc_curr.get(i).setZX(
			//		get_ZX_FZ(list_zc_curr.get(i).getF(),list_zc_curr.get(i).getT(),this.after_SXRule_list)
			//		/get_ZX_FM(list_zc_curr.get(i).getF(),this.after_SXRule_list));

					
			list_zc_curr.get(i).setZX(		
					get_ZX_FZ2(list_zc_curr.get(i).getF(),list_zc_curr.get(i).getT(),this.after_SXRule_list)		
					/get_ZX_FM2(list_zc_curr.get(i).getF()));
			
		}
		
		
		this.newRule_list=list_zc_curr;
		
	}
	
	
	/**
	 * @return
	 * panduan 在传入的集合中是否存在了，存在了就返回true
	 */
	private boolean IsExite(List<myRule_two> oldrule_list_curr,String tag1,String tag2){
		int oldrule_size=oldrule_list_curr.size();
		
		for(int i=0;i<oldrule_size;i++){
			
			if(tag1.equals(oldrule_list_curr.get(i).getF())){
				if(tag2.equals(oldrule_list_curr.get(i).getT())){
					return true;
				}
			}else if(tag1.equals(oldrule_list_curr.get(i).getT())){
				if(tag2.equals(oldrule_list_curr.get(i).getF())){
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * @param tag1
	 * @param tag2
	 * @param list_curr
	 * @return
	 * 计算置信度
	 * 获取分子的个数
	 */
	private double get_ZX_FZ(String tag1,String tag2,List<myRule_two> list_curr){
		double count =0;
		for(int i=0;i<list_curr.size();i++){
			if(tag1.equals(list_curr.get(i).getF())){
				if(tag2.equals(list_curr.get(i).getT())){
					count=list_curr.get(i).getCount();
					return count;
					
				}
			}else if(tag1.equals(list_curr.get(i).getT())){
				if(tag2.equals(list_curr.get(i).getF())){
					count=list_curr.get(i).getCount();
					return count;
				}
			}
		}
		
		return count;
	}
	//处理置信度最新 ，4.21
	private double get_ZX_FZ2(String tag1,String tag2,List<myRule_two> list_curr){
		double count =0;
		for(int i=0;i<list_curr.size();i++){
			if(tag1.equals(list_curr.get(i).getF())){
				if(tag2.equals(list_curr.get(i).getT())){
					count++;
				}
			}else if(tag1.equals(list_curr.get(i).getT())){
				if(tag2.equals(list_curr.get(i).getF())){
					count++;
				}
			}
		}
		
		return count;
	}
	
	//处理置信度分子计算的，4.21
	private double get_ZC_FZ(String tag1,String tag2,List<myRule_two> list_curr){
		double count =0;
		for(int i=0;i<list_curr.size();i++){
			if(tag1.equals(list_curr.get(i).getF())){
				if(tag2.equals(list_curr.get(i).getT())){
					count++;
				}
			}else if(tag1.equals(list_curr.get(i).getT())){
				if(tag2.equals(list_curr.get(i).getF())){
					count++;
				}
			}
		}
		
		return count;
	}
	
	
	/**
	 * @param tag
	 * @param list_curr
	 * @return
	 * 计算置信度
	 * 获取分母的个数
	 */
	private double get_ZX_FM(String tag,List<myRule_two> list_curr){
		double count=0;
		for(int i=0;i<list_curr.size();i++){
			if(tag.equals(list_curr.get(i).getF())){
				count=count+list_curr.get(i).getCount();
				
			}else if(tag.equals(list_curr.get(i).getT())){
				count=count+list_curr.get(i).getCount();
			}
		}
		
		return count;
	}
	
	/**
	 * @param tag
	 * @param list_curr
	 * @return
	 * 计算置信度方法2
	 * 获取分母的个数
	 */
	private double get_ZX_FM2(String tag){
		double count=0;
		for(int i=0;i<super.mashupList.size();i++){
			
			boolean b=is_apiexsitTag(tag,super.mashupList.get(i).getmApis());
			if(b==true){
				count++;
			}
		}
		
		return count;
	}
	
	private boolean is_apiexsitTag(String tag, List<String> apiscurr){
		
		for(int i=0;i<apiscurr.size();i++){
			List<String> strcurr=getTagsByApi(apiscurr.get(i));
			for(int j=0;j<strcurr.size();j++){
				if(tag.equals(strcurr.get(j))){
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * @param tag1
	 * @param tag2
	 * 新的元素加进去
	 */
	private void addOldlist(String tag1,String tag2){
		myRule_two rule=new myRule_two();
		rule.setF(tag1);
		rule.setT1(tag2);
		rule.setCount(1);
		
		this.oldRule_list.add(rule);
		
		
	}
	
	/**
	 * @param tag1
	 * @param tag2
	 *存在的的元素更新一下,也就是count+1
	 */
	private void updateOldlist(String tag1,String tag2){
		int oldsize=this.oldRule_list.size();
		
		for(int i=0;i<oldsize;i++){
			if(tag1.equals(this.oldRule_list.get(i).getF())){
				if(tag2.equals(this.oldRule_list.get(i).getT())){
					
					this.oldRule_list.get(i).setCount(1+(this.oldRule_list.get(i).getCount()));
					return;
				}
			}else if(tag1.equals(this.oldRule_list.get(i).getT())){
				if(tag2.equals(this.oldRule_list.get(i).getF())){
					
					this.oldRule_list.get(i).setCount(1+(this.oldRule_list.get(i).getCount()));
					return;	
				}
			}	
		}
	}
	
	
	
	
}
