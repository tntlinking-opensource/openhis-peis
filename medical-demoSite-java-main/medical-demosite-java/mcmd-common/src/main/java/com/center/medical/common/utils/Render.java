/**
 * @Render.java
 * @com.lingnet.util
 * @Description：
 * @author xuhp
 * @copyright 2018
 * @version V
 * @since 2018年1月17日
 */
package com.center.medical.common.utils;

import java.math.BigDecimal;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xuhp
 * @ClassName: Render
 * @Description:
 * @date 2018年1月17日 上午10:06:33
 */

public class Render {
    public static double getDouble(Object obj) {
        return obj == null ? 0.0 : Double.parseDouble(obj.toString());
    }

    public static String getClob(Object obj) {
        return obj == null ? "" : obj instanceof Clob ? ToolUtil.ClobToString((Clob) obj) : obj.toString();
    }

    public static String getIsNot(Object obj) {
        return obj == null ? "否" : "0".equals(obj.toString()) ? "否" : "是";
    }

    public static String getSfwc(Object obj) {
        return obj == null ? "" : "0".equals(obj.toString()) ? "内检" : "1".equals(obj.toString()) ? "外检" : "";
    }

    public static String getShzt(Object obj) {
        return obj == null ? "未审核" : "0".equals(obj.toString()) ? "未审核" : "1".equals(obj.toString()) ? "已审核" : "";
    }

    public static String getDate(Object obj, SimpleDateFormat sdf) {
        return obj == null ? "" : sdf.format(obj);
    }

    public static int getInt(Object obj) {
        return obj == null ? -1 : Integer.parseInt(obj.toString());
    }

    public static String getString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    public static String join(Collection<String> strs, String sep) {
        if (strs == null || strs.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            builder.append(str + sep);
        }
        return builder.substring(0, builder.length() - sep.length());
    }


    public static String getTbStatus(Object obj) {
        return obj == null ? "" : "0".equals(obj.toString()) ? "已提报" : "1".equals(obj.toString()) ? "已处理" : "";
    }

    public static String getUsecodehidden(Object obj) {
        return obj == null ? null : "1".equals(obj.toString()) ? "团检" : "个检";
    }

    public static String getIsCheck(Object obj) {
        if (obj == null) return "";
        String str = obj.toString();
        return "0".equals(str) ? "是" : "1".equals(str) ? "否" : "2".equals(str) ? "再通知" : "";
    }

    /**
     * 每日体检者折扣率
     */
    public static Double getZkl(Object moneyamountObj, Object yjhjObj) {
        if (yjhjObj == null) return null;
        if (moneyamountObj == null) return null;
        BigDecimal yjhj = new BigDecimal(yjhjObj.toString());
        if (yjhj.compareTo(BigDecimal.ZERO) == 0) return null;
        BigDecimal moneyamount = new BigDecimal(moneyamountObj.toString());
        return moneyamount.divide(yjhj, 2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    /**
     * 订单号
     */
    public static String getNumorgresv(Object obj) {
        if (obj == null) return "";
        String str = obj.toString();
        return "-1".equals(str) ? "" : str;
    }

    public static String getFamilyChargeIsMain(Object obj) {
        if (obj == null) {
            return "其他家庭成员";
        }
        switch (obj.toString()) {
            case "1":
                return "主持卡人";
            case "0":
                return "其他家庭成员";
            default:
                return "其他家庭成员";
        }
    }

    public static String getJhys(Map<String, String> harms, Object jhys) {
        if (jhys == null) return "";
        Set<String> set = new HashSet<String>();
        for (String jh : jhys.toString().split(",")) {
            if (harms.get(jh) != null) {
                set.add(harms.get(jh));
            }
        }
        return StringUtils.join(set, "、");
    }

    public static String getAudiometerNum(Object obj) {
        if (obj == null) return "";
        String testResult = obj.toString();
        int idx = testResult.indexOf("双耳高频听阈均值");
        if (idx == -1) {
            return "";
        }
        String back = testResult.substring(idx + 8);
        List<Integer> list = new ArrayList<Integer>();
        if (back.indexOf("db") != -1) list.add(back.indexOf("db"));
        if (back.indexOf("DB") != -1) list.add(back.indexOf("DB"));
        if (back.indexOf("dB") != -1) list.add(back.indexOf("dB"));
        if (back.indexOf("Db") != -1) list.add(back.indexOf("Db"));
        if (list.size() == 0) return "";
        Collections.sort(list);
        int idxpd = list.get(0);
        return back.substring(0, idxpd);
    }

    /**
     * 获取星期
     */
    public static String getDayForWeek(Object date) {
        if (date == null) return "";
        Date d;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        Integer dayForWeek = null;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek.toString();
    }

    /**
     * 分组类型
     * @param obj
     * @return
     */
    public static String getGroupType(Object obj){
        if(obj==null)return "";
        String str=obj.toString();
        return "0".equals(str)?"组内选":"1".equals(str)?"组间选":"2".equals(str)?"组任选":"";
    }


    /**
     * 获取体检类别
     * @param obj
     * @return
     */
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

    /**
     * 获取性别
     * @param obj
     * @return
     */
    public static String getSex(Object obj){
        return obj==null?"":"0".equals(obj.toString())?"男":"女";
    }


    public static String getTjlx(Object obj){
        return obj==null?"":"0".equals(obj.toString())?"健康"
                :"1".equals(obj.toString())?"职业"
                :"2".equals(obj.toString())?"综合":"复查";
    }

    public static String getOccupationDefend(Object obj) {
        return obj!=null&&"1".equals(obj.toString())?"有":"无";
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
}
