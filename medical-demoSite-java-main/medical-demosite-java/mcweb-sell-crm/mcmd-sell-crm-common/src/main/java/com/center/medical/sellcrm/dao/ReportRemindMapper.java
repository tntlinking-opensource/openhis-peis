package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.bean.model.Report;
import com.center.medical.sellcrm.bean.param.ReportRemindParam;
import com.center.medical.sellcrm.bean.vo.ReportRemindVo;
import org.apache.ibatis.annotations.Param;

/**
 * BG报告主表(Report)表数据库访问层
 *
 * @author ay
 * @since 2023-02-08 15:34:05
 */
public interface ReportRemindMapper extends BaseMapper<Report> {

    /**
    * 分页查询[BG报告主表]列表
    *
    * @param page 分页参数
    * @param param Report查询参数
    * @return 分页数据
    */
    IPage<ReportRemindVo> getList(PageParam<ReportRemindVo> page, @Param("param") ReportRemindParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Report getInfoById(@Param("id") String id);

}
