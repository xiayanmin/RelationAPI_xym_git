package com.xym.tools.R_aa_mashup;

import java.util.ArrayList;
import java.util.List;

import com.xym.model.myApi;
import com.xym.model.myMashup;
import com.xym.model.myRule_three;
import com.xym.model.myRule_two;
import com.xym.tools.R_parent;

/**
 * @author admin
 *处理api-mashup的二对一的关系,处理什么类型，要先调用一下setxxx_2t1()
 *这里要优化，加快计算速度
 */

public class R_aa_mashup extends R_parent{

	private List<myRule_three> myrulelist_aa_old=new ArrayList<>();//没筛选的aa二对一的关系
	private List<myRule_three> myrulelist_aa=new ArrayList<>();//最终的aa二对一的关系
	private List<myRule_three> myRulelist_mashup=new ArrayList<>();//最终的mashup二对一的关系
	
	public R_aa_mashup(List<myMashup> mashuplist,List<myApi> apilist){
		super();
		super.mashupList=mashuplist;
		super.apiList=apilist;

	}
	
	/**
	 * 调用这个，处理api的二对一规则
	 */
	public void setApi_2t1(){
		A();
	}
	
	/**
	 * 调用这个，处理mashup的二对一规则
	 */
	public void setMashup_2t1(){
		B();
	}
	
	/**
	 * @return
	 * 公共方法
	 * 返回aa的二对一关联规则
	 * 暂时返回old的
	 */
	public List<myRule_three> getmyRulelist_aa(){
		return this.myrulelist_aa;
	}
	
	
	/**
	 * @return
	 * 公共方法
	 * 返回mashup的二对一关联规则
	 */
	public List<myRule_three> getmyRulelist_mashup(){
		return this.myRulelist_mashup;
	}
	
	/**
	 * 程序处理aa关系的
	 */
	private void A(){
		System.out.println("3.11");
		for(int i=0;i<super.mashupList.size();i++){//控制mashup的跳转
			dealapitags(super.mashupList.get(i).getmApis());
		}
		
		for(int i=0;i<this.myrulelist_aa_old.size();i++){
			
			if(IsSame(this.myrulelist_aa_old.get(i).getF(),this.myrulelist_aa_old.get(i).getT(),this.myrulelist_aa_old.get(i).getT2())){
				//如果存在相同的，那么不处理
			}else{
				//如果不相同的话，就加到最终的aalist中
				this.myrulelist_aa.add(this.myrulelist_aa_old.get(i));
			}
		}
		
		double count=0;//标签对总数
		for(int i=0;i<this.myrulelist_aa.size();i++){
			count=count+this.myrulelist_aa.get(i).getCount();
		}
		
		//处理支持度,//处理置信度
		for(int i=0;i<this.myrulelist_aa.size();i++){
			
			this.myrulelist_aa.get(i).setZC(this.myrulelist_aa.get(i).getCount()/count);
			this.myrulelist_aa.get(i).setZX(this.myrulelist_aa.get(i).getCount()/get_zx_fm_aa(this.myrulelist_aa.get(i)));
		}
		
		
	}
	
	/**
	 * 程序处理mashup关系的
	 */
	private void B(){
		//先处理标签对
		for(int i=0;i<super.mashupList.size();i++){//控制mashup跳转的
			List<myRule_two> rule_two= getRuleTwoBytags(super.mashupList.get(i).getmTags());

			for(int j=0;j<rule_two.size();j++){
				
				for(int m=0;m<super.mashupList.get(i).getmTags().size();m++){
					if(super.mashupList.get(i).getmTags().get(m).equals(rule_two.get(j).getF())){//存在相同	
					}else if(super.mashupList.get(i).getmTags().get(m).equals(rule_two.get(j).getT())){//存在相同	
					}else{//不存在相同
						myRule_three rt_3=new myRule_three();
						rt_3.setF(rule_two.get(j).getF());
						rt_3.setT1(rule_two.get(j).getT());
						rt_3.setT2(super.mashupList.get(i).getmTags().get(m));
						
						
						if(IsExsit(rt_3, this.myRulelist_mashup)==2){//存在xiangtong，更新一下
							 update_mashup_List(rt_3);
						}else if(IsExsit(rt_3, this.myRulelist_mashup)==1){//存在xiangfan，加进去
							System.out.println(i+","+j+","+m);
							update_mashup_List(rt_3);
						}else if(IsExsit(rt_3, this.myRulelist_mashup)== 0){
							rt_3.setCount(1);
							this.myRulelist_mashup.add(rt_3);
						}
					}
				}
			}
		}
		

		
		
		
		//处理支持度
		double count=0;
		for(int i=0;i<this.myRulelist_mashup.size();i++){
			count =count +this.myRulelist_mashup.get(i).getCount();
		}
		
		for(int i=0;i<this.myRulelist_mashup.size();i++){
			this.myRulelist_mashup.get(i).setZC(this.myRulelist_mashup.get(i).getCount()/count);
		}
		
		//处理置信度
		for(int i=0;i<this.myRulelist_mashup.size();i++){
			
			this.myRulelist_mashup.get(i).setZX(this.myRulelist_mashup.get(i).getCount()/get_zx_fm_mashup(this.myRulelist_mashup.get(i)));
			
		}
		
		
	}
	
	private void dealapitags(List<String> tags){
		
		for(int i=0;i<tags.size();i++){
			for(int j=0;j<tags.size();j++){
				if(j==i){//相同的api是不用处理的
				}else{
					
					 setaa_old(getRuleTwoBytags(getTagsByApi(tags.get(i))),getTagsByApi(tags.get(j)));
					
				}
			}
		}
		
	}
	
	/**
	 * @return
	 * 传进来一组，返回一组两两组合的集合
	 */
	private List<myRule_two> getRuleTwoBytags(List<String> tags){
		
		List<myRule_two> myrule_curr=new ArrayList<>();
		
		for(int i=0;i<tags.size()-1;i++){
			for(int j=i+1;j<tags.size();j++){
				myRule_two rt=new myRule_two();
				rt.setF(tags.get(i));
				rt.setT1(tags.get(j));
				
				myrule_curr.add(rt);
			}
		}
		
		return myrule_curr;
	}
	
	/**
	 * @param rule_two
	 * @param tags
	 * 制作myrule_aa_old的集合
	 */
	private void setaa_old(List<myRule_two> rule_two,List<String> tags){
		
		for(int i=0;i<rule_two.size();i++){
			for(int j=0;j<tags.size();j++){
				myRule_three rt_3=new myRule_three();
				rt_3.setF(rule_two.get(i).getF());
				rt_3.setT1(rule_two.get(i).getT());
				rt_3.setT2(tags.get(j));
				
				if(IsExsit(rt_3,this.myrulelist_aa_old) == 1){//存在相反的
					update_aa_old_List(rt_3);
				}else if(IsExsit(rt_3,this.myrulelist_aa_old) == 2){//存在相同的
					update_aa_old_List(rt_3);
				}else if(IsExsit(rt_3,this.myrulelist_aa_old) == 0){//不存在
					rt_3.setCount(1);
					this.myrulelist_aa_old.add(rt_3);
				}
			}
		}
		
	}
	
	/**
	 * @param rule
	 * @param rulelist
	 * @return
	 * 判断rule是否已经存在了，存在相同就返回2，存在相反就返回1，不存在就返回0
	 */
	private int IsExsit(myRule_three rule,List<myRule_three> rulelist){
		int  Istrue=0;
		for(int i=0;i<rulelist.size();i++){
			if(rule.getT2().equals(rulelist.get(i).getT2())){
			
				if(rule.getF().equals(rulelist.get(i).getF())){
					if(rule.getT().equals(rulelist.get(i).getT())){
						Istrue=2;
					}
				}else if(rule.getF().equals(rulelist.get(i).getT())){
					if(rule.getT().equals(rulelist.get(i).getF())){
						Istrue=1;
					}
				}
				
				
				if(Istrue != 0){
					return Istrue;
				}
			}
			
		}
		
		return Istrue;
	}
	
	
	/**
	 * @param rt_3
	 * 更新aa_old集合，count+1
	 */
	private void update_aa_old_List(myRule_three rt_3){
		boolean Istrue=false;
		for(int i=0;i<this.myrulelist_aa_old.size();i++){
			if(rt_3.getT2().equals(this.myrulelist_aa_old.get(i).getT2())){
			
				if(rt_3.getF().equals(this.myrulelist_aa_old.get(i).getF())){
					if(rt_3.getT().equals(this.myrulelist_aa_old.get(i).getT())){
						Istrue=true;
					}
					
				}else if(rt_3.getF().equals(this.myrulelist_aa_old.get(i).getT())){
					if(rt_3.getT().equals(this.myrulelist_aa_old.get(i).getF())){
						Istrue=true;
					}
				}
				
				if(Istrue){
					this.myrulelist_aa_old.get(i).setCount(this.myrulelist_aa_old.get(i).getCount()+1);
					return;
				}
				
			}
		}
		
	}
	
	/**
	 * @param rt_3
	 * 更新mymashup集合，count+1
	 */
	private void update_mashup_List(myRule_three rt_3){
		boolean Istrue=false;
		
		for(int i=0;i<this.myRulelist_mashup.size();i++){
			if(rt_3.getT2().equals(this.myRulelist_mashup.get(i).getT2())){

				if(rt_3.getF().equals(this.myRulelist_mashup.get(i).getF())){
					if(rt_3.getT().equals(this.myRulelist_mashup.get(i).getT())){
					Istrue=true;
					}		
				}else if(rt_3.getF().equals(this.myRulelist_mashup.get(i).getT())){
					if(rt_3.getT().equals(this.myRulelist_mashup.get(i).getF())){
						Istrue=true;
					}	
				}
			}
			
			
			if(Istrue){
				this.myRulelist_mashup.get(i).setCount(1+this.myRulelist_mashup.get(i).getCount());
				return;
			}
			
			
		}
		
		
	}
	
	/**
	 * @param rt
	 * @return
	 * 获取对应myRule__three在myrulelist_aa中含有的个数，作为分母，用来计算置信度
	 */
	private double get_zx_fm_aa(myRule_three rt){
		double count=0;
		for(int i=0;i<this.myrulelist_aa.size();i++){
			if(rt.getF().equals(this.myrulelist_aa.get(i).getF())){
				if(rt.getT().equals(this.myrulelist_aa.get(i).getT())){
					count=count+this.myrulelist_aa.get(i).getCount();
				}
			}else if(rt.getF().equals(this.myrulelist_aa.get(i).getT())){

				if(rt.getT().equals(this.myrulelist_aa.get(i).getF())){
					count=count+this.myrulelist_aa.get(i).getCount();
				}
			}
		}
		
		return count;
	}
	
	/**
	 * @param rt
	 * @return
	 * 获取对应myRule__three在myrulelist_mashup中含有的个数，作为分母，用来计算置信度
	 */
	private double get_zx_fm_mashup(myRule_three rt){
		
		double count =0;
		for(int i=0;i<this.myRulelist_mashup.size();i++){
			if(rt.getF().equals(this.myRulelist_mashup.get(i).getF())){
				if(rt.getT().equals(this.myRulelist_mashup.get(i).getT())){
					count=count+this.myRulelist_mashup.get(i).getCount();
				}
			}else if(rt.getF().equals(this.myRulelist_mashup.get(i).getT())){
				if(rt.getT().equals(this.myRulelist_mashup.get(i).getF())){
					count=count+this.myRulelist_mashup.get(i).getCount();
				}
			}	
		}
		return count;
	}
}
