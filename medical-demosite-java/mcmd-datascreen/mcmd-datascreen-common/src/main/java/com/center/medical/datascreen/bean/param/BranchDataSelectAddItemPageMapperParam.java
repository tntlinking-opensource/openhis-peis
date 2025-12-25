package com.center.medical.datascreen.bean.param;

import lombok.Data;

import java.util.Date;

/**
 * 加项数据统计列表
 * @author xhp
 * @since 2023-06-17 9:10
 */
@Data
public class BranchDataSelectAddItemPageMapperParam {
    //分中心id
    private String branchId;

    private Date startTime1;

    private Date endTime1;

    private Date startTime2;

    private Date endTime2;

    private Date startTime3;

    private Date endTime3;
}
