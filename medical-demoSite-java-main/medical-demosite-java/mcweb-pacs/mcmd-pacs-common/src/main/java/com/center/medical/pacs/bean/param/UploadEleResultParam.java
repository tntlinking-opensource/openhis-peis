package com.center.medical.pacs.bean.param;

import com.center.medical.pacs.bean.dto.NumResultsDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 返回到主系统保存的数据
 */
@Data
public class UploadEleResultParam implements Serializable {
    //体检号
    private String patientcode;
    //检查时间
    private String time;
    //xml中的数值结果
    private List<NumResultsDto> numResultsDtoList;
}
