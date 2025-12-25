package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.ReportListDto;
import com.center.medical.bean.model.ReportShareMain;
import com.center.medical.bean.model.ReportShareTwo;
import com.center.medical.bean.param.AddReportShareParam;
import com.center.medical.bean.param.ReportSharePageParam;
import com.center.medical.bean.param.UpReportShareParam;
import com.center.medical.bean.param.ValidationCodeParam;
import com.center.medical.bean.vo.ReportShareMainVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 报告分享主表(ReportShareMain)服务接口
 *
 * @author ay
 * @since 2023-09-19 16:19:54
 */
public interface ReportShareMainService extends IService<ReportShareMain> {

    /**
     * 分页查询[报告分享主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReportShareMainVo> getPage(PageParam<ReportShareMainVo> page, ReportSharePageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportShareMain getInfoById(String id);


    /**
     * 分享报告
     * @param param
     * @return
     */
    ReportShareMainVo reportShare(AddReportShareParam param);

    /**
     * 更新分享报告
     * @param param
     * @return
     */
    String updateReportShare(UpReportShareParam param);


    /**
     * 分享报告统计-详情
     * @param id
     * @return
     */
    IPage<ReportShareTwo> details(PageParam<ReportShareTwo> page , String id);

    /**
     * 校验验证码并返回列表数据
     * @param param
     * @return
     */
    List<ReportListDto> validationCode(ValidationCodeParam param);

    /**
     * 分享报告过期
     * @return
     */
    Boolean reportShareMainExpire();
}

