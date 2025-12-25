package com.center.medical.machine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Report;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.machine.bean.param.VerificationCodeLoginParam;
import com.center.medical.machine.bean.vo.ReportPrintListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BG报告主表(Report)表数据库访问层
 *
 * @author ay
 * @since 2023-05-30 10:20:50
 */
public interface ReportPrintMapper extends BaseMapper<Report> {

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param Report查询参数
     * @return 分页数据
     */
    IPage<Report> getList(PageParam<Report> page, @Param("param") Report param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Report getInfoById(@Param("id") String id);

    /**
     * 报告打印列表
     * @param idcardno
     * @return
     */
    List<ReportPrintListVo> reportPrintList(@Param("param") VerificationCodeLoginParam param);
}
