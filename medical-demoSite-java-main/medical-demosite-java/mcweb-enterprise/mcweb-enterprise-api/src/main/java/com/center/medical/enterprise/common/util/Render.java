/**
 * @Render.java
 * @com.lingnet.util
 * @Description：
 * 
 * @author xuhp 
 * @copyright  2018
 * @version V
 * @since 2018年1月17日
 */
package com.center.medical.enterprise.common.util;


import com.center.medical.enterprise.common.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
 * @ClassName: Render 
 * @Description: 
 * @author xuhp
 * @date 2018年1月17日 上午10:06:33 
 *  
 */

public class Render {
	public static String getSex(Object obj){
		return obj==null?"":"0".equals(obj.toString())?"男":"女";
	}
	public static String getSexAill(Object obj){
		return obj==null?"0":"0".equals(obj.toString())?"1":"2";
	}
	public static String getObjSex(Object obj){
		return obj==null?"":"0".equals(obj.toString())?"男":"1".equals(obj.toString())?"女":"通用";
	}
	public static String getSfyhtc(Object obj){
		return obj==null?"":"0".equals(obj.toString())?"是":"1".equals(obj.toString())?"否":"通用";
	}
	
	public static double getDouble(Object obj){
		return obj==null?0.0:Double.parseDouble(obj.toString());
	}
	
//	public static String getClob(Object obj){
//		return obj==null?"":obj instanceof Clob?PublicUtil.ClobToString((Clob) obj):obj.toString();
//	}
	
	public static String getIsNot(Object obj){
		return obj==null?"否":"0".equals(obj.toString())?"否":"是";
	}
	
	public static String getSfwc(Object obj){
		return obj==null?"":"0".equals(obj.toString())?"内检":"1".equals(obj.toString())?"外检":"";
	}
	
	public static String getShzt(Object obj){
		return obj==null?"未审核":"0".equals(obj.toString())?"未审核":"1".equals(obj.toString())?"已审核":"";
	}
	
	public static String getDate(Object obj,SimpleDateFormat sdf){
		return obj==null?"":sdf.format(obj);
	}
	
	public static int getInt(Object obj){
		return obj==null?-1:Integer.parseInt(obj.toString());
	}
	
	public static String getString(Object obj){
		return obj==null?null:obj.toString();
	}
	
	public static String getTjlx(Object obj){
		return obj==null?"":"0".equals(obj.toString())?"健康"
				:"1".equals(obj.toString())?"职业"
						:"2".equals(obj.toString())?"综合":"复查";
	}
	
	public static String getTjzt(Object obj){
		Integer e=obj==null?null:Integer.parseInt(obj.toString());
		if(e==null){
			return "总检未开始";
		} else if (e == 0) {
            return "总检开始";
        } else if (e == 1) {
            return "总检完成";
        } else if (e == 2) {
            return "报告已打印";
        } else if (e == 3) {
            return "报告一审通过";
        } else if (e == 4) {
            return "报告一审不通过";
        } else if (e == 5) {
            return "二审通过";
        } else if (e == 6) {
            return "二审不通过";
        } else if (e == 7) {
            return "终审通过";
        } else if (e == 8) {
            return "终审不通过";
        } else if (e == 9) {
            return "报告已交接";
        } else if (e == 10) {
            return "报告已通知";
        } else if (e == 11) {
            return "报告已领取";
        } else {
            return null;
        }
	}
	
	public static String getKdgs(Object obj){
		if(obj==null){
			return null;
		}
		String str=obj.toString();
		switch(str){
		case "1":return "顺丰";
		case "2":return "EMS";
		case "3":return "圆通";
		case "4":return "申通";
		case "5":return "韵达";
		case "6":return "中通";
		case "7":return "其他";
		default:return null;
		}
	}
	
	public static String getMedicalType(Object obj){
		if(obj==null){
			return null;
		}
		switch(obj.toString()){
		case "0":return "上岗前";
		case "1":return "在岗期间";
		case "2":return "离岗时";
		case "3":return "离岗后";
		case "4":return "应急";
		default:return null;
		}
	}
	
	public static String join(Collection<String> strs,String sep){
		if(strs==null||strs.size()==0){
			return "";
		}
		StringBuilder builder=new StringBuilder();
		for(String str:strs){
			builder.append(str+sep);
		}
		return builder.substring(0,builder.length()-sep.length());
	}
	
	public static String getWjzjb(Object obj){
		if(obj==null){
			return null;
		}
		switch(obj.toString()){
		case "1":return "高";
		case "2":return "中";
		case "3":return "低";
		default:return null;
		}
	}
	
	public static String getTbStatus(Object obj){
		return obj==null?"":"0".equals(obj.toString())?"已提报":"1".equals(obj.toString())?"已处理":"";
	}
	
	public static String getHfclzt(Object obj){
		if(obj==null){
			return null;
		}
		switch(obj.toString()){
		case "0":return "已提报";
		case "1":return "继续提报";
		case "2":return "已完成";
		case "3":return "已取消";
		default:return null;
		}
	}
	
	public static String getZjclzt(Object obj){
		return obj!=null&&"1".equals(obj.toString())?"已完成":"未处理";
	}
	
	public static String getClfs(Object obj){
		if(obj==null){
			return null;
		}
		switch(obj.toString()){
		case "0":return "当面告知";
		case "1":return "电话通知";
		case "2":return "短信通知";
		case "3":return "继续提报";
		default:return null;
		}
	}
	public static String getUsecodehidden(Object obj){
		return obj==null?null:"1".equals(obj.toString())?"团检":"个检";
	}
	
	public static String getDrugStatus(Object obj){
		String str=obj.toString();
		return "0".equals(str)?"待处理":"1".equals(str)?"成交":"3".equals(str)?"退药":"未成交";
	}
	
	public static String getIsCheck(Object obj){
		if(obj==null)return "";
		String str=obj.toString();
		return "0".equals(str)?"是":"1".equals(str)?"否":"2".equals(str)?"再通知":"";
	}
	
	public static String getGroupType(Object obj){
		if(obj==null)return "";
		String str=obj.toString();
		return "0".equals(str)?"组内选":"1".equals(str)?"组间选":"2".equals(str)?"组任选":"";
	}
	/**
	 * 每日体检者折扣率
	 */
	public static Double getZkl(Object moneyamountObj,Object yjhjObj) {
		if(yjhjObj==null)return null;
		BigDecimal yjhj=new BigDecimal(yjhjObj.toString());
		if(yjhj.compareTo(BigDecimal.ZERO)==0)return null;
		BigDecimal moneyamount=new BigDecimal(moneyamountObj.toString());
		return moneyamount.divide(yjhj, 2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
	/**
	 * 订单号
	 */
	public static String getNumorgresv(Object obj) {
		if(obj==null)return "";
		String str=obj.toString();
		return "-1".equals(str)?"":str;
	}
	
	public static String getBellowQuestion(Object obj){
		if(obj==null){
			return null;
		}
		String str=obj.toString();
		switch(str){
		case "1":return "溶血";
		case "2":return "脂血";
		case "3":return "凝血";
		case "4":return "多采集";
		case "5":return "容器不符";
		case "6":return "标本量错误";
		case "7":return "标识不清";
		case "8":return "TCL";
		default:return null;
		}
	}
	
	public static String getFamilyChargeType(Object obj){
		if(obj==null){
			return null;
		}
		switch(obj.toString()){
		case "1":return "消费";
		case "0":return "充值";
		case "2":return "充值";
		default:return null;
		}
	}
	
	public static String getFamilyChargeIsMain(Object obj){
		if(obj==null){
			return "其他家庭成员";
		}
		switch(obj.toString()){
		case "1":return "主持卡人";
		case "0":return "其他家庭成员";
		default:return "其他家庭成员";
		}
	}
	
	public static String getJhys(Map<String,String> harms,Object jhys) {
		if(jhys==null)return "";
		Set<String> set=new HashSet<String>();
		for(String jh:jhys.toString().split(",")) {
			if(harms.get(jh)!=null) {
				set.add(harms.get(jh));
			}
		}
		return StringUtils.join(set, "、");
	}
	
	public static String getAudiometerNum(Object obj) {
		if(obj==null)return "";
		String testResult=obj.toString();
		int idx=testResult.indexOf("双耳高频听阈均值");
		if(idx==-1) {
			return "";
		}
		String back=testResult.substring(idx+8);
		List<Integer> list=new ArrayList<Integer>();
		if(back.indexOf("db")!=-1)list.add(back.indexOf("db"));
		if(back.indexOf("DB")!=-1)list.add(back.indexOf("DB"));
		if(back.indexOf("dB")!=-1)list.add(back.indexOf("dB"));
		if(back.indexOf("Db")!=-1)list.add(back.indexOf("Db"));
		if(list.size()==0)return "";
		Collections.sort(list);
		int idxpd=list.get(0);
		return back.substring(0, idxpd);
	}
	/**
	 * 获取星期
	 */
	public  static String getDayForWeek(Object date) {
		if(date==null)return "";
		Date d;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Calendar c=Calendar.getInstance();
		c.setTime(d);
		Integer dayForWeek = null;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek.toString();
	}
	
	public static String getClob(Object obj){
		return obj==null?"":obj instanceof Clob?ClobToString((Clob) obj):obj.toString();
	}
	
	public final static String ClobToString(Clob clob) {
        if (clob == null) {
            return null;
        }
        Reader is = null;
        String returnString = null;
        try {
            is = clob.getCharacterStream();
            BufferedReader br = new BufferedReader(is);

            String str = null;
            str = br.readLine(); // 读取第一行
            StringBuffer sb = new StringBuffer();
            while (str != null) { // 如果没有到达流的末尾，则继续读取下一行
                sb.append(str);
                str = br.readLine();
            }
            returnString = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnString;
    }
}
