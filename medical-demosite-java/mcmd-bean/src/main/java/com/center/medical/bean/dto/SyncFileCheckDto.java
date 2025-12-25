package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2024-04-10 10:47
 * @description: 文件同步记录检查：将用于判断文件是否成功同步到指定的地方
 */
@Data
public class SyncFileCheckDto implements Serializable {
    private static final long serialVersionUID = 9151135120748249170L;

    @ApiModelProperty(value = "业务记录主键ID")
    private String bizId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "文件路径", required = true)
    private String fileUrl;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "同步目的地：0线上同步到线下，1线下同步到线上", required = true)
    private Integer toFlag;

    @ApiModelProperty(value = "已执行的同步次数，初始值为0，超过5次就删掉")
    private Integer count;


    public SyncFileCheckDto() {
    }

    public SyncFileCheckDto(String bizId, String patientcode, String fileUrl, Date updateDate, Integer toFlag, Integer count) {
        this.bizId = bizId;
        this.patientcode = patientcode;
        this.fileUrl = fileUrl;
        this.updateDate = updateDate;
        this.toFlag = toFlag;
        this.count = count;
    }
}
