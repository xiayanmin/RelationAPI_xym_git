package com.xym.test;

import java.util.ArrayList;
import java.util.List;

public class dealtest {

	private List<String> str1=new ArrayList<>();
	private List<String> str2=new ArrayList<>();
	
	public List<modeltest> putstr=new ArrayList<>();
	
	public dealtest(List<String> s1,List<String> s2){
		this.str1=s1;
		this.str2=s2;
		
		dealA();
	}
	
	
	private void dealA(){
		
		List<String> str1curr=new ArrayList<>();//矿山名称
		List<String> str2curr=new ArrayList<>();//矿山解译
		
		for(int i=0;i<this.str1.size();i++){
			
			if(Is_1(str1.get(i),str1curr) == true){
				
				str1curr.add(str1.get(i));
				
			}else{//存在
			}
			
		}
		

		for(int i=0;i<this.str2.size();i++){
			
			if(Is_1(str2.get(i),str2curr) == true){
				
				str2curr.add(str2.get(i));
				
			}else{//存在
			}
			
		}
		
		int coun=0;
		
		for(int i=0;i<str1.size();i++){
			
			
			if(str1.get(i).equals(str1curr.get(coun))){
				
				
				
				
			}else{
				coun++;
				i--;
			}
			
			
		}
			
		
		
	}
	
	private boolean Is_1(String str,List<String> str1curr){
		for(int i=0;i<str1curr.size();i++){
			if(str.equals(str1curr.get(i))){
			 return false;	
			}

		}
		return true;
	}
	
}
