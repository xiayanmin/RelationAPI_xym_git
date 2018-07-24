package com.xym.model;

/**
 * @author admin
 *这个子类用来封装三个参数的规则
 *（F，T1）=>T2
 */
public class myRule_three extends myRule_two{

	private String T2;
	
	
	public myRule_three(){
		super();
	}
	
	//以下是封装
	public void setT2(String t2){
		this.T2=t2;
	}
	public String getT2(){
		return this.T2;
	}
}
