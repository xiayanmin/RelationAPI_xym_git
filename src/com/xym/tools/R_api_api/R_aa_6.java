package com.xym.tools.R_api_api;

import java.util.ArrayList;
import java.util.List;

import com.xym.model.myApi;
import com.xym.model.myMashup;
import com.xym.model.myRule_two_6;
import com.xym.tools.CallToWrite;
import com.xym.tools.R_parent;

/**
 * @author admin
 *制作每行的排列集合；
 *制作筛选集合；
 *筛选出符合的集合
 *计算支持度和置信度
 */
public class R_aa_6  extends R_parent{

	private List<myRule_two_6> oldRule_list=new ArrayList<>();//筛选之前的标签规则集合
	private List<myRule_two_6> SXrule_list=new ArrayList<>();//用于筛选的集合
	private List<myRule_two_6> after_SXRule_list=new ArrayList<>();//筛选后的标签规则集合不含有zc和zx
	private List<myRule_two_6> newRule_list=new ArrayList<>();//最终的标签规则集合含有支持度，不含有置信度
	private List<myRule_two_6> newRule_list2=new ArrayList<>();//最终的标签规则集合含有支持度和置信度,这才是最终的
	private double Allcount_new=0;//新总的标签对
	private double Allcount_old=0;//没筛选之前的标签对
	
	private boolean IsOK_1And2=false;//回调两个是否成功
	
	private CallToWrite calltowrite;//用来给开始写入对的
	
	long starttime,endtime;
	
	public R_aa_6(List<myMashup> mashuplist,List<myApi> apilist,CallToWrite calltowrite){
		super();
		super.mashupList=mashuplist;
		super.apiList=apilist;
		super.apilist2map();
		this.calltowrite=calltowrite;
		Set_1And2();
		
		starttime=System.currentTimeMillis();
	}
	//返回结果集合
	public List<myRule_two_6> getNewlist(){
		return this.newRule_list2;
	}

	//获得筛选后总的标签对
	public double getAllcount_after(){
		return this.Allcount_new;
	}
	
	//获得筛选前的标签对
	public double getAllcount_before(){
		return this.Allcount_old;
	}
	
	//通过api获取对应的tags
//	public List<String> getTByA(String api){
//		return super.getTagByApi2(api);
//	}
	
	//制作每行排雷集合，并异步制作筛选集合
	private void Set_1And2(){
		//异步制作筛选集合
		intercall_SX SXcall=new intercall_SX(super.mashupList, super.apiList, this);
		Thread t2=new Thread(SXcall);
		t2.start();
		
		//制作筛选集合
		intercall_OLD OLDcall=new intercall_OLD(super.mashupList, super.apiList, this);
		Thread t1=new Thread(OLDcall);
		t1.start();
	}
	//回调筛选集合，回调成功判断排列集合是否成功，成功进入第三步
	private void myCallback_SX(List<myRule_two_6> SXrule_list){
		this.SXrule_list=SXrule_list;
		
		if(IsOK_1And2==true){
			Set_3();
		}else{
			IsOK_1And2=true;
		}
	}
	
	//回调排列集合,回调成功判断筛选是否成功，成功就进入第三步
	private void myCallback_OLD(List<myRule_two_6> oldRule_list){
		this.oldRule_list=oldRule_list;
		
		if(IsOK_1And2==true){
			Set_3();
		}else{
			IsOK_1And2=true;
		}
	}
	
	//回调最后newlist的处理置信度的
	//处理完成回调
	private void myCallback_setnewlist(List<myRule_two_6> newule_list_part){
		this.newRule_list2.addAll(newule_list_part);
		System.out.println("线程回一次");
		if(this.newRule_list.size() == this.newRule_list2.size()){
			
			this.calltowrite.StartWrite();//这里是用来回调的写入文件的
		}
		
	}
	
	//制作筛选后的集合，并处理标签对数目
	private void Set_3(){
		endtime=System.currentTimeMillis();
		System.out.println("6-Set_1And2用时:"+(endtime-starttime));
		starttime=System.currentTimeMillis();
		
		//处理前中的标签对数目
		int c_old=0;
		for(int i=0;i<this.oldRule_list.size();i++){
			c_old=this.oldRule_list.get(i).getCountList().size();
			Allcount_old=Allcount_old+c_old;
		}
		
		
		for(int i=0;i<this.oldRule_list.size();i++){
			if(IsExist_3(this.oldRule_list.get(i).getF(),this.oldRule_list.get(i).getT())){
			}else{
				this.after_SXRule_list.add(this.oldRule_list.get(i));
			}
		}
		
		//处理总的标签对数目
		int c_new=0;
		for(int i=0;i<this.after_SXRule_list.size();i++){
			c_new=this.after_SXRule_list.get(i).getCountList().size();
			Allcount_new=Allcount_new+c_new;
			this.after_SXRule_list.get(i).setCount(c_new);
		}
		
		endtime=System.currentTimeMillis();
		System.out.println("6-Set_3用时:"+(endtime-starttime));
		starttime=System.currentTimeMillis();
		Set_4();
	}
	
	//处理支持度，置信度
	private void Set_4(){
		int mashup_size=super.mashupList.size();
		//先处理支持度，因为前后都是一样的。t1=>t2与t2=>t1的支持度一样的
		for(int i=0;i<this.after_SXRule_list.size();i++){
			this.after_SXRule_list.get(i).setZC(this.after_SXRule_list.get(i).getCount()/mashup_size);
		}
		
		//处理置信度.线补rule，然后处理置信度
		for(int i=0;i<this.after_SXRule_list.size();i++){
			myRule_two_6 rule1=this.after_SXRule_list.get(i);
			
			myRule_two_6 rule2=new myRule_two_6();
			rule2.setF(this.after_SXRule_list.get(i).getT());
			rule2.setT1(this.after_SXRule_list.get(i).getF());
			rule2.setCount(this.after_SXRule_list.get(i).getCount());
			rule2.setZC(this.after_SXRule_list.get(i).getZC());
			
			this.newRule_list.add(rule1);
			this.newRule_list.add(rule2);
		}
		endtime=System.currentTimeMillis();
		System.out.println("6-Set_4_1用时:"+(endtime-starttime));
		starttime=System.currentTimeMillis();
		
		System.out.println("newlist的数量:"+newRule_list.size());
//		for(int i=0;i<this.newRule_list.size();i++){
//			long starttime=System.currentTimeMillis();
//			this.newRule_list.get(i).setZX(this.newRule_list.get(i).getCount()/get_ZX_FM2(this.newRule_list.get(i).getF()));
//			long endtime=System.currentTimeMillis();
//			System.out.println("set置信度第"+i+"个用时:"+(endtime-starttime));
//		}
//		
		setnewlist2();
		endtime=System.currentTimeMillis();
		System.out.println("6-Set_4_2用时:"+(endtime-starttime));
		
	}
	
	//处理置信度的详细方法
	//并行处理，根据总的记录数，已7000为限,生成新的线程并发处理
	private void setnewlist2(){
		int s=8000;
		int threadnum=this.newRule_list.size()/s;
		if(this.newRule_list.size() == (threadnum*s)){
			
		}else{
			threadnum++;
		}
		
		System.out.println("线程一共："+threadnum);
		List<myRule_two_6> list_part=new ArrayList<>();
		for(int i=0;i<this.newRule_list.size();i++){
			if(this.newRule_list.get(i) ==null){
				System.out.print(i);
			}else{
				myRule_two_6 rule1=this.newRule_list.get(i);
				list_part.add(rule1);
				if(i==this.newRule_list.size()-1){
					intercall_setnewlist newlistcall=new intercall_setnewlist(list_part, this,threadnum);
					Thread t=new Thread(newlistcall);
					t.start();
					list_part=new ArrayList<>();
				}else{
					if(list_part.size()==s){
						intercall_setnewlist newlistcall=new intercall_setnewlist(list_part, this,(i+1)/s);
						Thread t=new Thread(newlistcall);
						t.start();
						list_part=new ArrayList<>();
					}
				}
			}
		}
		System.out.println(list_part.size());
//		for(int i=0;i<list_part.size();i++){
//			list_thread.get(i).start();
//		}
	}
	//返回求置信度分母
	private double get_ZX_FM(String tag){
		
		//获取api的集合，获取对应的api的tag
		long starttime=System.currentTimeMillis();
		int c=0;
		for(int i=0;i<super.mashupList.size();i++){
			List<String> apilist_curr=super.mashupList.get(i).getmApis();
			for(int j=0;j<apilist_curr.size();j++){
				List<String> tagslist_curr=getTagsByApi(apilist_curr.get(j));
				for(int k=0;k<tagslist_curr.size();k++){
					if(tagslist_curr.get(k).equals(tag)){
						c++;
						k=tagslist_curr.size();
						j=apilist_curr.size();
					}
				}
			}
		}
		long endtime=System.currentTimeMillis();
		System.out.println("6-get_ZX_FM2用时"+tag+":"+(endtime-starttime));
		return c;
	}
	
	//返回求置信度分母
		private double get_ZX_FM2(String tag){
			
			//获取api的集合，获取对应的api的tag
			//long starttime=System.currentTimeMillis();
			int c=0;
			for(int i=0;i<super.mashupList.size();i++){
				List<String> apilist_curr=super.mashupList.get(i).getmApis();
				for(int j=0;j<apilist_curr.size();j++){
					List<String> tagslist_curr=getTagByApi2(apilist_curr.get(j));
					for(int k=0;k<tagslist_curr.size();k++){
						if(tagslist_curr.get(k).equals(tag)){
							c++;
							k=tagslist_curr.size();
							j=apilist_curr.size();
						}
					}
				}
			}
			//long endtime=System.currentTimeMillis();
			//System.out.println("6-get_ZX_FM2用时"+tag+":"+(endtime-starttime));
			return c;
		}
	
	
	
	//检查先筛选集合中是否 存在old的标签
	private boolean IsExist_3(String tag1,String tag2){
		for(int i=0;i<this.SXrule_list.size();i++){
			if(tag1.equals(this.SXrule_list.get(i).getF())){
				if(tag2.equals(this.SXrule_list.get(i).getT())){
					return true;
				}
			}else if(tag1.equals(this.SXrule_list.get(i).getT())){
				if(tag2.equals(this.SXrule_list.get(i).getF())){
					return true;
				}
			}

		}
		return false;
	}

	//制作每行的排列集合
	private class intercall_OLD implements Runnable{
		private R_aa_6 R_aa_6;
		
		private List<myMashup> mashuplist;
		private List<myApi> apilist;
		private List<myRule_two_6> oldRule_list;//筛选之前的标签规则集合
		
		public intercall_OLD(List<myMashup> mashuplist,List<myApi> apilist,R_aa_6 R_aa_6){
			this.R_aa_6=R_aa_6;
			this.apilist=apilist;
			this.mashuplist=mashuplist;
			oldRule_list=new ArrayList<>();
		}

		@Override
		public void run() {
			//制作每行的排列集合。
			for(int i=0;i<this.mashuplist.size();i++){
				setOldlist(i);
			}
			
			R_aa_6.myCallback_OLD(this.oldRule_list);
		}
		//制作排列集合的总方法
		private void setOldlist(int im){
			List<String> list_curr=this.mashuplist.get(im).getmApis();
			int s=list_curr.size();
			for(int i=0;i<s-1;i++){
				for(int j=i+1;j<s;j++){
					deal_PL(list_curr.get(i),list_curr.get(j),im);
				}
			}
		}
		//yonglai处理api与api间的
		private void deal_PL(String api1,String api2,int im){
			List<String> tags1=getTagsByApi(api1);
			List<String> tags2=getTagsByApi(api2);
			
			for(int i=0;i<tags1.size();i++){
				for(int j=0;j<tags2.size();j++){
					if(tags1.get(i).equals(tags2.get(j))){
					}else{
						int c=IsExist(this.oldRule_list, tags1.get(i), tags2.get(j));
						
						if(c==-1){//不存在
							Addlist(tags1.get(i), tags2.get(j), im);
						}else{//存在的
							UpdateList(c,im);
						}
					}
				}
			}
		}
		
		//判断同一行是否已经存在了
		private int  IsExist(List<myRule_two_6> list_curr,String tag1,String tag2){
			for(int i=0;i<list_curr.size();i++){
				
				if(tag1.equals(list_curr.get(i).getF())){
					if(tag2.equals(list_curr.get(i).getT())){
						return i;
					}
				}else if(tag1.equals(list_curr.get(i).getT())){
					if(tag2.equals(list_curr.get(i).getF())){
						return i;
					}
				}
			}
			
			return -1;
		}
		
		//存在就更新一下、im1是oldlist中的index，im2是mashuplist中的index
		private void UpdateList(int im1,int im2){
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
		
		//不存在就添加进去
		private void Addlist(String tag1,String tag2,int im){
			myRule_two_6 rule=new myRule_two_6();
			rule.setF(tag1);
			rule.setT1(tag2);	
			rule.addCountList(im);
			
			this.oldRule_list.add(rule);
		}
		
	}
	
	//制作筛选集合的类
	private class intercall_SX implements Runnable{
		private R_aa_6 R_aa_6;
		
		
		private List<myMashup> mashuplist;
		private List<myApi> apilist;
		private List<myRule_two_6> SXrule_list;
		
		
		public intercall_SX(List<myMashup> mashuplist,List<myApi> apilist,R_aa_6 R_aa_6){
			this.R_aa_6=R_aa_6;
			this.apilist=apilist;
			this.mashuplist=mashuplist;
			SXrule_list=new ArrayList<>();
		}
		
		@Override
		public void run() {
			//这里写上制作筛选的集合
			/*for(int i=0;i<this.mashuplist.size();i++){
				for(int j=0;j<this.mashuplist.get(i).getmApis().size();j++){
					setSXlist(this.mashuplist.get(i).getmApis().get(j));
				}
			}*/
			
			
	        //是否过滤掉api内部的标签关联规则。		
			for(int i=0;i<this.apilist.size();i++){
				setSXlist2(this.apilist.get(i).getaTags());
			}
		
			R_aa_6.myCallback_SX(this.SXrule_list);
		}
		
		//制作的筛选集合的总方法
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
		
		//制作的筛选集合的总方法,补充api其中没有的
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
		
		//判断同一行是否已经存在了
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
	}
	
	//获取一段newlist，计算其中的置信度
	private  class intercall_setnewlist implements Runnable{
		
		private List<myRule_two_6> newRule_list;//传进来的newrulelist，也用于传出
		private R_aa_6 R_aa_6;
		private int myid;
		
		public intercall_setnewlist(List<myRule_two_6> newRule_list,R_aa_6 R_aa_6,int id){
			this.newRule_list=newRule_list;
			this.R_aa_6=R_aa_6;
			this.myid=id;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			myStart();
			R_aa_6.myCallback_setnewlist(this.newRule_list);
		}
		
		private void myStart(){
			for(int i=0;i<this.newRule_list.size();i++){
				long starttime=System.currentTimeMillis();
				this.newRule_list.get(i).setZX(this.newRule_list.get(i).getCount()/get_ZX_FM2(this.newRule_list.get(i).getF()));
				long endtime=System.currentTimeMillis();
				System.out.println("线程"+myid+"计算置信度第"+i+"个用时:"+(endtime-starttime));
			}
		}
		
	}
}
