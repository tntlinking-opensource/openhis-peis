package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * pacs参数配置
 * @author xhp
 * @since 2024-05-27 8:53
 */
@Data
public class PacsConfig implements Serializable {
    //pacs类型,com.center.medical.common.enums.PacsType.name()
    private List<String> pacsTypes;
    //pacs线程自动获取多少天内md_peispatient表发生改变的数据(modifydate)
    private Integer daysAgo;
    //自动获取pacs结果的科室编号。如果没有配置，获取所有ksType=1的科室。
    private List<String> deptNos;
}
