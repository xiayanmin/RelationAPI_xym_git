package com.xym.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 *6.0新的对象
 */
public class myRule_two_6 extends myRule_two {

	private List<Integer> countlist=new ArrayList<>();//存储所在行数
	
	public myRule_two_6(){
		//空
		super();
	}
	
	public List<Integer> getCountList(){
		return this.countlist;
	}
	
	public void addCountList(int i){
		Integer I=i;
		this.countlist.add(I);
	}
	
	public String getF(){
		return super.getF();
	}
	
	public String getT(){
		return super.getT();
	}
	
	public double getZX(){
		return super.getZX();
	}
	
	public double getZC(){
		return super.getZC();
	}
	
	public double getCount(){
		return super.getCount();
	}
	
	public void setCount(int count){//添加list
		
		double im=count;
		super.setCount(im);
	}
	public void setF(String f){
		super.setF(f);
	}
	
	public void setT1(String t1){
		super.setT1(t1);
	}
	
	public void setZC(double zc){
		super.setZC(zc);
	}
	
	public void setZX(double zx){
		super.setZX(zx);
	}
}
