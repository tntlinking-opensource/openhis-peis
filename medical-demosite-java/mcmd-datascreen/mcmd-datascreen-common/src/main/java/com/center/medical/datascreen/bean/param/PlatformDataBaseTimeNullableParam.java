package com.center.medical.datascreen.bean.param;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 时间可以不传参数
 * @author xhp
 * @since 2023-06-01 15:16
 */
public class PlatformDataBaseTimeNullableParam {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间 yyyy-MM-dd HH:mm:ss",required = false)
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间 yyyy-MM-dd HH:mm:ss",required = false)
    private Date endTime;

    public PlatformDataBaseTimeNullableParam() {
    }

    public PlatformDataBaseTimeNullableParam(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
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
}
