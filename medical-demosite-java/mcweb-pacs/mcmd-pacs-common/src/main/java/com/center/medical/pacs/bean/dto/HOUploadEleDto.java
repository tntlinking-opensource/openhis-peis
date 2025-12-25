package com.center.medical.pacs.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HOUploadEleDto implements Serializable {
    private static final long serialVersionUID = 117234837611267932L;

    private String patientcode;//体检号
    private String origionName;//上传文件的名称
    private String ksId;//科室id
    private String creatDate;//创建时间
    private String doctor;//医生
    private List<EleDataDto> eleDataDtoList;//上传文件的名称
}
