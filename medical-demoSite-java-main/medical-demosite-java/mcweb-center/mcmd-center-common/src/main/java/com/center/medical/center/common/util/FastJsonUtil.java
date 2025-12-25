/**
 * @FastJsonUtil.java
 * @com.lingnet.util
 * @Description：
 * 
 * @author xuhp 
 * @copyright  2019
 * @version V
 * @since 2019年3月1日
 */
package com.center.medical.center.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @ClassName: FastJsonUtil 
 * @Description:  
 * @author xuhp
 * @date 2019年3月1日 上午9:10:57 
 *  
 */

public class FastJsonUtil {
	/**
	 * fastjson getDate方法无法识别SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");格式Date
	 * 艾迪康日期字段存在多种格式
	 * @Title: getDate 
	 * @param obj
	 * @param key
	 * @param sdfs
	 * @return 
	 * Date 
	 * @author xuhp
	 * @since 2019年3月1日 V 1.0
	 */
	public static Date getDate(JSONObject obj,String key,SimpleDateFormat ...sdfs){
		String str=obj.getString(key);
		if(StringUtils.isEmpty(str)){
			return null;
		}
		for(SimpleDateFormat sdf:sdfs){
			try {
				return sdf.parse(str);
			} catch (ParseException e) {
			}
		}
		throw new RuntimeException("时间字符串格式不匹配："+str);
	}
}
