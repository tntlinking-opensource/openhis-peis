package com.center.medical.report.bean.param;

import com.center.medical.report.bean.model.BgFormData;
import com.center.medical.report.bean.model.BgGriddata;
import com.center.medical.report.bean.model.ReGriddata;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 保存更新复查通知单
 */
@Data
public class SaveReviewParam implements Serializable {
    private static final long serialVersionUID = 949267112319992691L;

    @ApiModelProperty(value = "体检号")
    private String patientno;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户id，这个参数不需要添")
    private String userId;

    @ApiModelProperty(value = "复查时间 起始")
    private Date dateFrom;

    @ApiModelProperty(value = "保存表格数据")
    private List<ReGriddata> ReGriddatas;

    @ApiModelProperty(value = "复查注意事项")
    private String noticeOfProceedingText;

    @ApiModelProperty(value = "复查时间 结束")
    private Date dateTo;
}
