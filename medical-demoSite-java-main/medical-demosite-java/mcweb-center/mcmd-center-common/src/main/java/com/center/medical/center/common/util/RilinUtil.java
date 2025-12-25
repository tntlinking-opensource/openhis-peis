package com.center.medical.center.common.util;

import cn.hutool.core.util.StrUtil;
import com.center.medical.common.config.RilinConfig;
import com.center.medical.common.utils.ip.IpUtils;

import java.util.List;

/**
 * 对接瑞林萨尔健康管理系统
 * @author xhp
 * @since 2025-04-08 8:24
 */
public class RilinUtil {

    /**
     * 判断是否是有权限执行线程的ip（只允许一个ip运行瑞林萨尔上传任务）
     * @param rilinConfig
     * @return
     */
    public static boolean isTaskAuthorizedIp(RilinConfig rilinConfig){
        //如果不限制ip,可以执行线程
        if(rilinConfig.getIsIpAuth()==null || !(rilinConfig.getIsIpAuth().booleanValue()))return true;

        //如果限制ip但没配置Ip，不可以执行线程
        String configIp=rilinConfig.getIp();
        if(StrUtil.isEmpty(configIp))return false;

        //获取内网IP地址
        List<String> ips = IpUtils.getInnerHostIp();

        //内网ip中包含配置ip时，可以执行线程，否则不可以
        return ips.contains(configIp);
    }

    /**
     * 新系统id是19位，老系统id是32位。
     * 老系统是uuid，不会重复，不需要拼接，如果是老系统id直接跳过，如果不跳过会超过id字段长度限制32。
     * 为了不改变默认排序，拼在后面。
     * 为了能区分后缀，增加下划线。
     * @param id
     * @param jm 分中心简码
     * @return
     */
    public static String handleId(String id,String jm){
        if(jm==null)jm="";
        String newId;
        if(id.length()>=32){
            newId=id;
        }else if(id.length()+jm.length()>32){
            newId=id;
        }else{
            newId=id+"_"+jm;
        }
        return newId;
    }
}
