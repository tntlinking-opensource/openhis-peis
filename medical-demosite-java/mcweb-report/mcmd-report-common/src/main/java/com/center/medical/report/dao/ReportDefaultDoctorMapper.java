package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ReportDefaultDoctor;
import com.center.medical.report.bean.param.DefaultDoctorPageParam;
import com.center.medical.report.bean.vo.ReportDefaultDoctorVo;
import org.apache.ibatis.annotations.Param;

/**
 * 报告中科室默认医生(ReportDefaultDoctor)数据库访问层
 *
 * @author ay
 * @since 2024-08-21 16:54:18
 */
public interface ReportDefaultDoctorMapper extends BaseMapper<ReportDefaultDoctor> {

    /**
     * 分页查询[报告中科室默认医生]列表
     *
     * @param page  分页参数
     * @param param ReportDefaultDoctor查询参数
     * @return 分页数据
     */
    IPage<ReportDefaultDoctorVo> getPage(PageParam<ReportDefaultDoctorVo> page, @Param("param") DefaultDoctorPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportDefaultDoctor getInfoById(@Param("id") String id);

    /**
     * 通过科室id和类型查询
     * @param ksId
     * @param type
     * @return
     */
    String getinfoByKsIdAndType(@Param("ksId") String ksId, @Param("type") int type,@Param("personnelType") int personnelType);
}
