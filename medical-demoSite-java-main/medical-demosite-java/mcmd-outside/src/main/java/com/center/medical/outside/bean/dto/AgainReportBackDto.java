package com.center.medical.outside.bean.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2025/6/20 10:22
 * @description: 
 */
@Data
public class AgainReportBackDto implements Serializable {
    private static final long serialVersionUID = -1901993245212269915L;
    private String OrderID;
    private String Msg;
    private String State;
}
