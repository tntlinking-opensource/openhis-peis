package com.center.medical.center.qingdao.profession.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;

/**
 * 特殊处理工具类
 * @author xhp
 * @since 2024-01-12 9:28
 */
@Slf4j
public class SpecialTreatmentUtil {

    /**
     * itam_code去重   体检系统两个危害因素，硫酸、三氧化硫，云平台就一个硫酸及三氧化硫，不能传重复
     * @param itamCode
     * @return
     */
    public static String treatItamCode(String itamCode){
        if(StrUtil.isEmpty(itamCode))return itamCode;
        String[] arr=itamCode.split(",");
        //危害因素名称与危害因素代码的顺序必须相同 + set去重复
        LinkedHashSet<String> set=new LinkedHashSet<>();
        for(String str:arr){
            set.add(str);
        }
        return StringUtils.join(set,',');
    }


    public static void main(String[] args){
        log.info(treatItamCode("1,2,3,4,1,1,1,1,2"));
    }
}
