package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * lis参数配置
 * @author xhp
 * @since 2023-11-20 11:10
 */
@Data
public class LisConfig implements Serializable {
    private static final long serialVersionUID = -5008465477929520545L;
    //lis类型  com.center.medical.common.enums.LisType
    private List<String> lisTypes;
    //是否特殊处理
    private Boolean isSpecial;
    //lis线程自动获取多少天内md_peispatient表发生改变的数据(modifydate)
    private Integer daysAgo;
}
