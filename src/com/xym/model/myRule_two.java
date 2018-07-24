package com.xym.model;


/**
 * @author admin
 *这是两个标签的标签对组合的对象；
 *F=>T
 */
public class myRule_two {

	private String F;//第一个标签
	private String T1;//第二个标签
	private double ZC;//支持度
	private double ZX;//置信度
	
	
	private double count=-1;//行数
	
	
	public myRule_two(){
		//空
	}
	
	//以下是封装
	public String getF(){
		return this.F;
	}
	
	public void setF(String f){
		this.F=f;
	}
	
	public String getT(){
		return this.T1;
	}
	
	public void setT1(String t1){
		this.T1=t1;
	}
	
	public double getZC(){
		return this.ZC;
	}
	
	public void setZC(double zc){
		this.ZC=zc;
	}
	
	public double getZX(){
		return this.ZX;
	}
	
	public void setZX(double zx){
		this.ZX=zx;
	}
	
	public void setCount(double count){
		this.count=count;
	}
	
	public double getCount(){
		return this.count;
	}
}
