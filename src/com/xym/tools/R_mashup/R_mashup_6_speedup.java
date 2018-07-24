package com.xym.tools.R_mashup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xym.model.myApi;
import com.xym.model.myMashup;
import com.xym.model.myRule_two_6;
import com.xym.tools.CallToWrite;
import com.xym.tools.R_parent;

/**
 * @author admin
 *分离两个方法，筛选和不筛选
 */
public class R_mashup_6_speedup extends R_parent {

	private int allcount;
		
	private List<myRule_two_6> oldRule_list=new ArrayList<>();//筛选之前的标签规则集合
	private List<myRule_two_6> SXrule_list=new ArrayList<>();//用于筛选的集合
	
	private Map<String,myRule_two_6> SXrule_map=new HashMap<>();//用于筛选的map
	
	private List<myRule_two_6> after_SXRule_list=new ArrayList<>();//筛选后的标签规则集合不含有zc和zx
	//private List<myRule_two_6> Rule_list=new ArrayList<>();//结果的规则集合
	
	private boolean IsOK_1=false;//制作筛选集合和old集合回调两个是否成功
	
	private int type=0;//默认0,1是代表处理没有sx的方法
	
	private CallToWrite calltowrite;//用来给开始写入对的
	
	public R_mashup_6_speedup(List<myMashup> mashuplist,List<myApi> apilist,CallToWrite calltowrite){
		super();
		super.mashupList=mashuplist;
		super.apiList=apilist;
		this.calltowrite=calltowrite;
	}
	
	/**
	 * 开启用筛选的方法
	 */
	public void OpenWithSX(){
		CreatOldlist();
		CreatSXlist();
	}
	
	/**
	 * 开启没有筛选的方法
	 */
	public void OpenWithNoSX(){
		type=1;
		CreatOldlistWithNoSX();
	}
	
	public List<myRule_two_6> getRuseltList(){
		return this.after_SXRule_list;
	}
	
	/**
	 * 开启线程制作原始集合
	 */
	private void CreatOldlist(){
		creatOldlist runab=new creatOldlist(this.mashupList, this.apiList, this);
		Thread t=new Thread(runab);
		t.start();
	}
	
	private void CreatOldlistWithNoSX(){
		creatOldlist runab=new creatOldlist(this.mashupList, this.apiList, this);
		Thread t=new Thread(runab);
		t.start();
	}
	
	/**
	 * 开启线程制作筛选的集合
	 */
	private void CreatSXlist(){
		creatSxlist runab=new creatSxlist(this.mashupList, this.apiList, this);
		Thread t=new Thread(runab);
		t.start();
	}
	
	/**
	 * 开启线程将原本的筛选的list集合转变为map，增快搜索的速度
	 */
	private void CreatSXmapFromlist(){
		creatSxMapFromlist runab=new creatSxMapFromlist(this.SXrule_list, this);
		Thread t=new Thread(runab);
		t.start();
	}
	
	/**
	 * 根据筛选的map集合，和原始的list，制作筛选后的list
	 */
	private void CreatAfterRulelist(){
		//循环原始list，每个标签对和map比较Ixsit，存在就不做处理，不存在就添加到新的集合中add
		for(int i=0;i<this.oldRule_list.size();i++){
			if(IsExist(this.oldRule_list.get(i).getF(),this.oldRule_list.get(i).getT())){
				//存在的话就不处理
			}else{
				//不存在就加进去
				this.after_SXRule_list.add(this.oldRule_list.get(i));
			}
		}
		//这里可以加入统计标签对数目的方法
		int count=0;
		for(int i=0;i<this.after_SXRule_list.size();i++){
			this.after_SXRule_list.get(i).setCount(this.after_SXRule_list.get(i).getCountList().size());
			count=count+(int)this.after_SXRule_list.get(i).getCount();
		}
		System.out.println("标签总数:"+count);
		
		//制作后就可以用这个了
		CreatRulelist();
	}
	
	/**
	 * 制作最后的结果集合
	 */
	private void CreatRulelist(){
		//根据
		//先写入 count,补全支持度
		for(int i=0;i<this.after_SXRule_list.size();i++){
			this.after_SXRule_list.get(i).setCount(this.after_SXRule_list.get(i).getCountList().size());
			this.after_SXRule_list.get(i).setZC(this.after_SXRule_list.get(i).getCount()/super.mashupList.size());
		}
		
		//补全置信度
		List<myRule_two_6> listcurr=new ArrayList<>();
		for(int i=0;i<this.after_SXRule_list.size();i++){

			myRule_two_6 rule1=this.after_SXRule_list.get(i);
			
			myRule_two_6 rule2=new myRule_two_6();
			rule2.setF(this.after_SXRule_list.get(i).getT());
			rule2.setT1(this.after_SXRule_list.get(i).getF());
			rule2.setCount(this.after_SXRule_list.get(i).getCount());
			rule2.setZC(this.after_SXRule_list.get(i).getZC());
			
			listcurr.add(rule1);
			listcurr.add(rule2);
		}
		
		this.after_SXRule_list=null;
		this.after_SXRule_list=listcurr;
		
		for(int i=0;i<this.after_SXRule_list.size();i++){
			this.after_SXRule_list.get(i).setZX(this.after_SXRule_list.get(i).getCount()/get_ZX_FM(this.after_SXRule_list.get(i).getF()));
		}
		calltowrite.StartWrite();
		
	}
	
	/**
	 * 制作无筛选的最后的结果集合
	 */
	private void CreatRulelistNoSX(){
		//根据
		//先写入 count,补全支持度
		for(int i=0;i<this.oldRule_list.size();i++){
			this.oldRule_list.get(i).setCount(this.oldRule_list.get(i).getCountList().size());
			this.oldRule_list.get(i).setZC(this.oldRule_list.get(i).getCount()/super.mashupList.size());
		}
		
		//这里可以加入统计标签对数目的方法
		int count=0;
		for(int i=0;i<this.oldRule_list.size();i++){
			this.oldRule_list.get(i).setCount(this.oldRule_list.get(i).getCountList().size());
			count=count+(int)this.oldRule_list.get(i).getCount();
		}
		System.out.println("未筛选标签总数:"+count);				
				
		
		//补全置信度
		List<myRule_two_6> listcurr=new ArrayList<>();
		for(int i=0;i<this.oldRule_list.size();i++){

			myRule_two_6 rule1=this.oldRule_list.get(i);
			
			myRule_two_6 rule2=new myRule_two_6();
			rule2.setF(this.oldRule_list.get(i).getT());
			rule2.setT1(this.oldRule_list.get(i).getF());
			rule2.setCount(this.oldRule_list.get(i).getCount());
			rule2.setZC(this.oldRule_list.get(i).getZC());
			
			listcurr.add(rule1);
			listcurr.add(rule2);
		}
		
		this.after_SXRule_list=null;
		this.after_SXRule_list=listcurr;
		
		for(int i=0;i<this.after_SXRule_list.size();i++){
			this.after_SXRule_list.get(i).setZX(this.after_SXRule_list.get(i).getCount()/get_ZX_FM(this.after_SXRule_list.get(i).getF()));
		}
		calltowrite.StartWrite(1);		
	}
	
	
	
	/**
	 * @param Oldrule_list
	 * @param SXrule_list
	 * 同步开始制作筛选和原始规则标签，同回调的方法，都结束掉后将sxlist转为sxmap
	 */
	private void Callback_oldAndSX_list(List<myRule_two_6> Oldrule_list,List<myRule_two_6> SXrule_list){
		if(type ==1 ){
			this.oldRule_list=Oldrule_list;
			CreatRulelistNoSX();
		}else{
			if(Oldrule_list ==null && SXrule_list !=null){
				this.SXrule_list=SXrule_list;
				if(IsOK_1 == true){
					CreatSXmapFromlist();
				}
				
				this.IsOK_1=true;
				System.out.println("R_mashup_6_speedup oldRule_list："+this.SXrule_list.size());
				return;
			}
			
			if(SXrule_list ==null && Oldrule_list !=null){
				this.oldRule_list=Oldrule_list;
				if(IsOK_1 == true){
					CreatSXmapFromlist();
				}
				
				this.IsOK_1=true;
				System.out.println("R_mashup_6_speedup oldRule_list："+this.oldRule_list.size());
				return;
			}
			
		}
		
	}

	
	/**
	 * @param SXmap
	 * 线程处理list转map 的回调方法，回调之后一般是用筛选map把原始rulelist筛选掉
	 */
	private void Callback_SXlistToMap(Map<String,myRule_two_6> SXmap){
		this.SXrule_map=SXmap;
		System.out.println("筛选map得到，共"+SXmap.size()+"条");
		CreatAfterRulelist();
		
	}
	
	
	/**
	 * @return
	 * 判断是否存在了
	 */
	private boolean IsExist(String tag1,String tag2){
		String str1=tag1+"=>"+tag2;
		String str2=tag2+"=>"+tag1;
		
		if(this.SXrule_map.get(str1) == null){
			if(this.SXrule_map.get(str2) == null){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
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
	
	/**
	 * @author admin
	 *用来异步制作oldlist
	 */
	private class creatOldlist implements Runnable{
		private List<myMashup> mashuplist;
		private List<myApi> apilist;
		private List<myRule_two_6> oldRule_list;//筛选之前的标签规则集合
		
		private R_mashup_6_speedup R_;
		
		public creatOldlist(List<myMashup> mashuplist,List<myApi> apilist,R_mashup_6_speedup R_){
			this.mashuplist=mashuplist;
			this.apilist=apilist;
			this.R_=R_;
			this.oldRule_list=new ArrayList<>();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			creatList();
			this.R_.Callback_oldAndSX_list(oldRule_list, null);
		}
		
		/**
		 * 制作list的入口
		 */
		private void creatList(){
			for(int i=0;i<this.mashuplist.size();i++){
				dealTags(this.mashuplist.get(i).getmTags(), i);
			}
		}
		
		private void dealTags(List<String> tags,int im){
			//是否相同，是否存在
			for(int i=0;i<tags.size()-1;i++){
				for(int j=i+1;j<tags.size();j++){
					if(tags.get(i).equals(tags.get(j))){
						//如果相同不处理
					}else{
						//不相同的话
						int c=IsExist(tags.get(i), tags.get(j));
						if(c == -1){//不存在
							Addlist(tags.get(i), tags.get(j),im);
						}else{//存在了
							Updatelist(c, im);
						}
					}
				}
			}
		}
		
		/**
		 * @param list_curr
		 * @param tag1
		 * @param tag2
		 * @return
		 * 是否已经存在了
		 */
		private int IsExist(String tag1,String tag2){
			for(int i=0;i<this.oldRule_list.size();i++ ){
				if(tag1.equals(this.oldRule_list.get(i).getF()) && tag2.equals(this.oldRule_list.get(i).getT())){
					return i;
				}else if(tag2.equals(this.oldRule_list.get(i).getF()) && tag1.equals(this.oldRule_list.get(i).getT())){
					return i;
				}
			}
			
			return -1;
		}
		
		//不存在就添加进去
		private void Addlist(String tag1,String tag2,int im){
			myRule_two_6 rule=new myRule_two_6();
			rule.setF(tag1);
			rule.setT1(tag2);	
			rule.addCountList(im);
			
			this.oldRule_list.add(rule);
		}
		
		//存在就更新一下、im1是oldlist中的index，im2是mashuplist中的index				
		private void Updatelist(int im1,int im2){
			boolean b=false;
			
			for(int i=0;i<this.oldRule_list.get(im1).getCountList().size();i++){
				if(im2 == this.oldRule_list.get(im1).getCountList().get(i).intValue()){
					b=true;
				}
			}
			if(b==true){
				b=false;
			}else{
				this.oldRule_list.get(im1).addCountList(im2);
			}
			
		}
		
	}
	
	/**
	 * @author admin
	 *用来异步制作sxlist
	 *试用于一般的数据量
	 *这里直接循环的全部的apilist
	 */
	private class creatSxlist implements Runnable{
		private List<myMashup> mashuplist;
		private List<myApi> apilist;
		private List<myRule_two_6> SXrule_list;
		private R_mashup_6_speedup R_;
		
		public creatSxlist(List<myMashup> mashuplist,List<myApi> apilist,R_mashup_6_speedup R_){
			this.mashuplist=mashuplist;
			this.apilist=apilist;
			this.R_=R_;
			this.SXrule_list=new ArrayList<>();
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			creatList();
			R_.Callback_oldAndSX_list(null, this.SXrule_list);
		}
		
		/**
		 * 制作SXlist
		 * 
		 */
		private void creatList(){
			for(int i=0;i<this.apilist.size();i++){	
				dealTags(this.apilist.get(i).getaTags());
			}
		}
		
		/**
		 * @param api
		 * 根据api，处理一个api的tags
		 */
		private void dealTags(List<String> apitags){
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
								Addtolist(tags.get(i),tags.get(j));
							}
						}
					}
				}
			}
				
		}
		
		/**
		 * @param rule
		 * rule加入sxlist
		 */
		private void Addtolist(String tag1,String tag2){
			myRule_two_6 rule=new myRule_two_6();
			rule.setF(tag1);
			rule.setT1(tag2);
			this.SXrule_list.add(rule);
		}
		
		/**
		 * @param Listcurr
		 * @param tag1
		 * @param tag2
		 * 判断是否存在
		 */
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
	}
	
	/**
	 * @author admin
	 *用来异步制作sxmap
	 */
	private class creatSxMapFromlist implements Runnable{
		private List<myRule_two_6> list;
		private Map<String,myRule_two_6> map=new HashMap<>();
		
		private R_mashup_6_speedup R_;
		
		public creatSxMapFromlist(List<myRule_two_6> list,R_mashup_6_speedup R_){
			this.list=list;
			this.R_=R_;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			listTomap();
			R_.Callback_SXlistToMap(map);
		}
		private void listTomap(){
			for(int i=0;i<this.list.size();i++){
				String str=this.list.get(i).getF()+"=>"+this.list.get(i).getT();
				map.put(str, this.list.get(i));
			}
		}
		
	}
	
	
	
}
