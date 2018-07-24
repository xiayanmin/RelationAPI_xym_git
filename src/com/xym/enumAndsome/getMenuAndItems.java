package com.xym.enumAndsome;

import java.util.ArrayList;
import java.util.List;

public class getMenuAndItems {

	/**
	 * @return
	 * 
	 * 提供主菜单的名称
	 */
	public static List<String> getMenuName(){
		List<String> menuName=new ArrayList<>();
		
		menuName.add("预处理");
		menuName.add("分步处理");
		menuName.add("快捷处理");
		menuName.add("帮助");
		
		return menuName;
	}
	
	
	/**
	 * @return
	 * 提供子菜单的名称
	 */
	public static List<List<String>> getMenuItemsName(){
		List<List<String>> itemsName=new ArrayList<>();
		
		List<String> list1=new ArrayList<>();
		list1.add("预处理");
		
		List<String> list2=new ArrayList<>();
		list2.add("API间标签对数");
		list2.add("API间关联标签规则");
		list2.add("Mashup标签对数");
		list2.add("Mashup关联标签规则");
		list2.add("API间二对一关联标签规则");
		list2.add("Mashup二对一关联标签规则");
		
		List<String> list3=new ArrayList<>();
		list3.add("一步处理");
		
		List<String> list4=new ArrayList<>();
		list4.add("帮助");
		
		itemsName.add(list1);
		itemsName.add(list2);
		itemsName.add(list3);
		itemsName.add(list4);
		
		return itemsName;
	}
	
	
	/**
	 * @return
	 * 获取api与api规则输出的excel的表头
	 */
	public static String[] getTitles_11(){
		String[] strs=new String[]{"关联规则","支持度","置信度"};
			return strs;
	}
	
	
	/**
	 * @return
	 * 获取api集合的表头
	 */
	public static String[] getTitles_0_api(){
		String[] strs=new String[]{"apiname","apitags"};
		return strs;
	}
	
	
	/**
	 * @return
	 * 获取mashup集合的表头
	 */
	public static String[] getTitle_0_mashup(){
		String[] strs=new String[]{"mashupname","mashuptags","apigroup"};
		return strs;
	}
}
