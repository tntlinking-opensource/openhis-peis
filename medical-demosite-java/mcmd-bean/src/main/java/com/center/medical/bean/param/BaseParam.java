package com.center.medical.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 路飞
 * @date: 2022-10-14 13:47
 * @description: 基础请求参数
 */
public class BaseParam implements Serializable {
    private static final long serialVersionUID = 5421021060821948357L;

    @ApiModelProperty(value = "分中心ids,传参时多个则以英文逗号（,）隔开")
    private List<String> branchIds;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 请求参数
     */
    @ApiParam(hidden = true)
    private Map<String, Object> params;

    public List<String> getBranchIds() {
        return branchIds;
    }

    public void setBranchIds(List<String> branchIds) {
        this.branchIds = branchIds;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
