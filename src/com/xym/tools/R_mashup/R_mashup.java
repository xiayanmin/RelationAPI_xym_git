package com.xym.tools.R_mashup;

import java.util.ArrayList;
import java.util.List;

import com.xym.model.myMashup;
import com.xym.model.myRule_two;
import com.xym.tools.R_parent;

/**
 * @author admin
 *处理mashup的规则
 *直接使用方式，初始化之后，就可以调用getCount(),和getRuleList()使用
 */
public class R_mashup extends R_parent{
	
	private double allCount=0;//总的标签对数
	private List<myRule_two> Rule_list=new ArrayList<>();//结果的规则集合
	
	
	/**
	 * @param mashuplist
	 * 初始化，传进来一个mashup的集合
	 */
	public R_mashup(List<myMashup> mashuplist){
		super();
		super.mashupList=mashuplist;
		A();
		B();
	}
	
	
	/**
	 * @return
	 * 返回标签对数目
	 */
	public double getCount(){
		return this.allCount;
	}
	
	public List<myRule_two> getRuleList(){
		return this.Rule_list;
	}

	
	/**
	 * 程序的主方法，
	 */
	private void A(){
		
		int mashuplistsize=super.mashupList.size();
		
		
		for(int i=0;i<mashuplistsize;i++){
			//System.out.println("mashup每行中的遍历:"+i);
			setRule_list(super.mashupList.get(i).getmTags());
		}
		
		double count=0;
		for(int i=0;i<this.Rule_list.size();i++){
			count=count+this.Rule_list.get(i).getCount();
			//System.out.println("mashup中的遍历:"+i);
		}
		this.allCount=count;
		
	}
	
	
	/**
	 * 补全支持度和置信度
	 */
	private void B(){
		//补全支持度
		for(int i=0;i<this.Rule_list.size();i++){
			//this.Rule_list.get(i).setZC(this.Rule_list.get(i).getCount()/this.allCount);
			this.Rule_list.get(i).setZC((get_ZC_FZ(this.Rule_list.get(i).getF(), this.Rule_list.get(i).getT(), this.Rule_list)*2)/super.mashupList.size());
		}
		
		List<myRule_two> list_zc_curr=new ArrayList<>();
		//补全置信度
		for(int i=0;i<this.Rule_list.size();i++){
			myRule_two rule1=this.Rule_list.get(i);
			
			myRule_two rule2=new myRule_two();
			rule2.setF(this.Rule_list.get(i).getT());
			rule2.setT1(this.Rule_list.get(i).getF());
			rule2.setCount(0);
			rule2.setZC(this.Rule_list.get(i).getZC());
			list_zc_curr.add(rule1);
			list_zc_curr.add(rule2);
		}
		
		
		for(int i=0;i<list_zc_curr.size();i++){
		//list_zc_curr.get(i).setZX(
		//		get_ZX_FZ(list_zc_curr.get(i).getF(), list_zc_curr.get(i).getT(), list_zc_curr)
		//		/get_ZX_FM(list_zc_curr.get(i).getF(), list_zc_curr));
		
		list_zc_curr.get(i).setZX(
				get_ZX_FZ2(list_zc_curr.get(i).getF(), list_zc_curr.get(i).getT(), list_zc_curr)
				/get_ZX_FM2(list_zc_curr.get(i).getF()));
		}
		
		this.Rule_list=list_zc_curr;
		
		
	}
	
	/**
	 * @param tags
	 * 处理每一个mahup的tags,不出力支持度和置信度
	 */
	private void setRule_list(List<String> tags){
		
		for(int i=0;i<tags.size()-1;i++){
			for(int j=i+1;j<tags.size();j++){
				if(IsExsit(tags.get(i), tags.get(j))){
					UpdateList(tags.get(i), tags.get(j));
				}else{
					addTolist(tags.get(i),  tags.get(j));
				}
			}
			
			//System.out.println("mashup中的遍历:"+i);
		}
		
	}
	
	
	/**
	 * @param tag1
	 * @param tag2
	 * @return
	 * 判断是否存在了标签,存在的话就返回true
	 */
	private boolean IsExsit(String tag1,String tag2){
		boolean Istrue=false;
		
		for(int i=0;i<this.Rule_list.size();i++){
			if(tag1.equals(this.Rule_list.get(i).getF())){
				if(tag2.equals(this.Rule_list.get(i).getT())){
					Istrue=true;
				}
			}else if(tag1.equals(this.Rule_list.get(i).getT())){
				if(tag2.equals(this.Rule_list.get(i).getF())){
				Istrue=true;	
				}
			}
			
			if(Istrue){
				return Istrue;
			}
		}
		
		return Istrue;
	}
	
	
	/**
	 * 新标签对的加到标签对集合
	 */
	private void addTolist(String tag1,String tag2){
		
		myRule_two rule=new myRule_two();
		rule.setF(tag1);
		rule.setT1(tag2);
		rule.setCount(1);
		
		this.Rule_list.add(rule);
	}
	
	
	/**
	 * 存在了的更新一下标签对,也就是count+1
	 */
	private void UpdateList(String tag1,String tag2){
		boolean Istrue =false;
		
		for(int i=0;i<this.Rule_list.size();i++){
			
			if(tag1.equals(this.Rule_list.get(i).getF())){
				if(tag2.equals(this.Rule_list.get(i).getT())){
					Istrue=true;
				}
			}else if(tag1.equals(this.Rule_list.get(i).getT())){
				if(tag2.equals(this.Rule_list.get(i).getF())){
				Istrue=true;	
				}
			}
			
			if(Istrue){
				this.Rule_list.get(i).setCount(this.Rule_list.get(i).getCount()+1);
				return;
				
			}
		}	
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
	
	//处理支持度最新，4.21
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
	 * 计算置信度2
	 * 获取分母的个数
	 */
	private double get_ZX_FM2(String tag){
		double count=0;
		for(int i=0;i<super.mashupList.size();i++){
			boolean b=is_mashupexsitTag(tag,super.mashupList.get(i).getmTags());
			if(b==true){
				count++;
			}
		}
			
		return count;
	}
	private boolean is_mashupexsitTag(String tag, List<String> tagscurr){
		for(int i=0;i<tagscurr.size();i++){
			if(tag.equals(tagscurr.get(i))){
				return true;
			}
		}
		
		return false;
	}
}
