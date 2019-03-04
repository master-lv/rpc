package com.metro.bi.util;


import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: 字符串工具类
 *
 * @author:DELL
 * @Date:2018/10/30 13 55
 * @Description:
 */      
public class StringUtil {

	private static final Logger log=LoggerFactory.getLogger(StringUtil.class);

	/**     
	 * @description 给定字符串是否为空或空串
	 * @author DELL
	 * @Date:2018/10/30 13 55
	 * @param str
	 * @return  boolean
	 */
	public static boolean isEmpty(String str) {
		if (null ==str  || str.trim().length() == 0) return true;
		return false;
	}

	public static void main(String[] ar){
//		String tableName="sms_HIStORY";
//		log.info(tableToJava(tableName));
//		log.info(fromOracle2Java(tableName));

//		parseDate("2018.4.18-19");
//		parseDate("2018-04-18/21");

		System.out.println("\u63A5\u53E3url");

	}

	public static List<Map<String,String>> columnToJava(List<Map<String,String>> tableValues, Properties prop) {
		List<Map<String, String>> attrValues = new ArrayList<>();
		Map<String, String> attrValue = null;
		for (Map<String, String> tableValue : tableValues){
			// 处理属性名
			attrValue = new HashMap<>();
			String columnName = tableValue.get("columnName");
			String[] strs = columnName.split("_");
			String attrName = "";
			for (int i=0; i<strs.length; i++){
				if (i != 0){
//					strs[i] =  WordUtils.capitalize(strs[i]);
				}
				attrName += strs[i];
			}
			attrValue.put("attrName", attrName);

			// 处理属性类型
			String columnType = tableValue.get("columnType") + "(";
			String attrType = columnType.substring(0,columnType.indexOf("("));
			// 根据properties中的类型字典配置将数据库类型转化为java类型
			attrValue.put("attrType", prop.getProperty(attrType));

			// 其余属性不改变直接封装入attrValue返回
			attrValue.put("attrNull",tableValue.get("columnNull"));
			attrValue.put("attrKey",tableValue.get("columnKey"));
			attrValue.put("attrDefault",tableValue.get("columnDefault"));
			attrValue.put("attrComment",tableValue.get("columnComment"));

			// 单条属性封入属性列表
			attrValues.add(attrValue);
		}
		return attrValues;
	}

	public static String[] parseDate(String dateContent){
		String beginDate="",endDate="";
		String[] tmp,ret=new String[2];
		String patt="\\d{4}\\.\\d{1,2}\\.\\d{1,2}(\\-\\d{1,2})?";
		String patt2="\\d{4}\\-\\d{1,2}\\-\\d{1,2}(\\/\\d{1,2})?";
		if(Pattern.matches(patt,dateContent)){
			tmp=dateContent.split("-");
			if(tmp.length>1){
				beginDate=tmp[0];
				endDate=tmp[1];
				endDate=beginDate.substring(0,beginDate.lastIndexOf(".")+1)+endDate;
			}else{
				beginDate=tmp[0];
				endDate=tmp[0];
			}
		}
		if(Pattern.matches(patt2,dateContent)){
			tmp=dateContent.split("/");
			if(tmp.length>1){
				beginDate=tmp[0];
				endDate=tmp[1];
				endDate=beginDate.substring(0,beginDate.lastIndexOf("-")+1)+endDate;
			}else{
				beginDate=tmp[0];
				endDate=tmp[0];
			}
		}
		ret[0]=beginDate;ret[1]=endDate;
		return ret;
	}

	// 处理类名
	public static String tableToJava(String tableName) {
		String regex = "([a-zA-Z]+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(tableName);
		String className = "";
		while (matcher.find()){
			className += WordUtils.capitalize(matcher.group());
		}
		return className;
	}

	public static String fromOracle2Java(String oracleTableName){
		String[] tmp=oracleTableName.split("_");
		StringBuffer sb=new StringBuffer();
		for(String s:tmp){
			sb.append(WordUtils.capitalize(s));
		}
		return sb.toString();
	}
}
