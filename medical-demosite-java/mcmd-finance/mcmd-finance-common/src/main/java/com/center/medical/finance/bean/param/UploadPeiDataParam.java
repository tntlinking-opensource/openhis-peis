package com.center.medical.finance.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传体检者收费数据 参数
 */
@Data
public class UploadPeiDataParam implements Serializable {
    private static final long serialVersionUID = 5394473117623937248L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "sys_branch表里的centerorgname,从线上传的时候需要,线下不用")
    private String centerorgname;


    public UploadPeiDataParam(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public UploadPeiDataParam() {
    }
}
