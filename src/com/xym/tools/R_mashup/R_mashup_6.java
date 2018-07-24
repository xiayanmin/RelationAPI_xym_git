package com.xym.tools.R_mashup;

import java.util.ArrayList;
import java.util.List;

import com.xym.model.myApi;
import com.xym.model.myMashup;
import com.xym.model.myRule_two_6;
import com.xym.tools.R_parent;
import com.xym.tools.R_api_api.R_aa_6;

/**
 * @author admin
 *先做出排列的集合；
 *处理支持度和置信度
 */
public class R_mashup_6 extends R_parent {

	private double allCount=0;//总的标签对数
	private List<myRule_two_6> Rule_list=new ArrayList<>();//结果的规则集合
	private List<myRule_two_6> SXrule_list;
	private List<myApi> apilist;
	
	//添加api筛选集合开始
	
	private void setSXlist(String api){
		if(api.equals("")){
			return;
		}else{
			List<String> tags=getTagsByApi(api);
			
			for(int i=0;i<tags.size()-1;i++){
				
				for(int j=i+1;j<tags.size();j++){//是否存在，存在就不做处理，不存在就加上去
					if(tags.get(i).equals(tags.get(j))){
						
					}else{
						if(IsExist(this.SXrule_list, tags.get(i), tags.get(j))){
							
						}else{
							Addlist(tags.get(i),tags.get(j));
						}
					}
				}
			}
		}
	}
	
	private void setSXlist2(List<String> apitags){
		if(apitags.size()==0 || apitags==null){
			return;
		}else{
			List<String> tags=apitags;
			
			for(int i=0;i<tags.size()-1;i++){
				
				for(int j=i+1;j<tags.size();j++){//是否存在，存在就不做处理，不存在就加上去
					if(tags.get(i).equals(tags.get(j))){
						
					}else{
						if(IsExist(this.SXrule_list, tags.get(i), tags.get(j))){
							
						}else{
							Addlist(tags.get(i),tags.get(j));
						}
					}
				}
			}
		}
	}
	
	private boolean IsExist(List<myRule_two_6> list_curr,String tag1,String tag2){
		for(int i=0;i<list_curr.size();i++){
			
			if(tag1.equals(list_curr.get(i).getF())){
				if(tag2.equals(list_curr.get(i).getT())){
					return true;
				}
			}else if(tag1.equals(list_curr.get(i).getT())){
				if(tag2.equals(list_curr.get(i).getF())){
					return true;
				}
			}
		}
		
		return false;
	}
	
	//不存在就添加进去
	private void Addlist(String tag1,String tag2){
		myRule_two_6 rule=new myRule_two_6();
		rule.setF(tag1);
		rule.setT1(tag2);
		this.SXrule_list.add(rule);
	}
	
	//添加api筛选集合结束
	
	
	public R_mashup_6(List<myMashup> mashuplist){
		super();	
		super.mashupList=mashuplist;
		set_1();
		set_2();
	}
	
	public double getCount(){
		return this.allCount;
	}
	
	public List<myRule_two_6> getRule_list(){
		return this.Rule_list;
	}
	
	//制作标签排列集合
	private void set_1(){
		for(int i=0;i<super.mashupList.size();i++){
			setRulelist(super.mashupList.get(i).getmTags(),i);
		}
	}
	
	//补充支持度和置信度
	private void set_2(){
		//先写入 count,补全支持度
		for(int i=0;i<this.Rule_list.size();i++){
			this.Rule_list.get(i).setCount(this.Rule_list.get(i).getCountList().size());
			this.Rule_list.get(i).setZC(this.Rule_list.get(i).getCount()/super.mashupList.size());
		}
		
		//补全置信度
		List<myRule_two_6> listcurr=new ArrayList<>();
		for(int i=0;i<this.Rule_list.size();i++){

			myRule_two_6 rule1=this.Rule_list.get(i);
			
			myRule_two_6 rule2=new myRule_two_6();
			rule2.setF(this.Rule_list.get(i).getT());
			rule2.setT1(this.Rule_list.get(i).getF());
			rule2.setCount(this.Rule_list.get(i).getCount());
			rule2.setZC(this.Rule_list.get(i).getZC());
			
			listcurr.add(rule1);
			listcurr.add(rule2);
		}
		
		this.Rule_list=null;
		this.Rule_list=listcurr;
		
		for(int i=0;i<this.Rule_list.size();i++){
			this.Rule_list.get(i).setZX(this.Rule_list.get(i).getCount()/get_ZX_FM(this.Rule_list.get(i).getF()));
		}
		
		
	}
	
	//获取置信度的分母
	private double get_ZX_FM(String tag){
		double c=0;
		for(int i=0;i<super.mashupList.size();i++){
			List<String> taglist_curr=super.mashupList.get(i).getmTags();
			for(int j=0;j<taglist_curr.size();j++){
				if(tag.equals(taglist_curr.get(j))){
					c++;
					j=taglist_curr.size();
				}else{
				}
			}
		}
		return c;
	}
	
	//具体处理第一步的
	private void setRulelist(List<String> tags,int im){
		//是否相同，是否存在
		for(int i=0;i<tags.size()-1;i++){
			for(int j=i+1;j<tags.size();j++){
				if(tags.get(i).equals(tags.get(j))){
					//如果相同不处理
				}else{
					//不相同的话
					int c=IsExist_1(tags.get(i), tags.get(j));
					if(c == -1){//不存在
						Addlist_1(tags.get(i), tags.get(j),im);
					}else{//存在了
						Updatelist_1(c, im);
					}
				}
			}
		}
	}
	
	//看似否已经存在了
	private int IsExist_1(String tag1,String tag2){
		for(int i=0;i<this.Rule_list.size();i++ ){
			if(tag1.equals(this.Rule_list.get(i).getF()) && tag2.equals(this.Rule_list.get(i).getT())){
				return i;
			}else if(tag2.equals(this.Rule_list.get(i).getF()) && tag1.equals(this.Rule_list.get(i).getT())){
				return i;
			}
		}
		
		return -1;
	}
	//im1是rulelist中的，im2是mashup中的
	private void Updatelist_1(int im1,int im2){
		
		boolean b=false;
		
		for(int i=0;i<this.Rule_list.get(im1).getCountList().size();i++){
			if(im2 == this.Rule_list.get(im1).getCountList().get(i).intValue()){
				b=true;
			}
		}
		if(b==true){
			b=false;
		}else{
			this.Rule_list.get(im1).addCountList(im2);
		}
		
	}
	
	private void Addlist_1(String tag1,String tag2,int im){
		myRule_two_6 rule=new myRule_two_6();
		rule.setF(tag1);
		rule.setT1(tag2);	
		rule.addCountList(im);
		
		this.Rule_list.add(rule);
	}
	
}
