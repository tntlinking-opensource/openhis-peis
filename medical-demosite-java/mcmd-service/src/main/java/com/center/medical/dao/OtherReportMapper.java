package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.OtherReport;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 其他报告(OtherReport)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:48
 */
public interface OtherReportMapper extends BaseMapper<OtherReport> {

    /**
     * 分页查询[其他报告]列表
     *
     * @param page  分页参数
     * @param param OtherReport查询参数
     * @return 分页数据
     */
    IPage<OtherReport> getList(PageParam<OtherReport> page, @Param("param") OtherReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OtherReport getInfoById(@Param("id") String id);

    /**
     * 根据体检号和类型查询
     *
     * @param patientcode
     * @param type
     * @return
     */
    OtherReport getInfoByCode(@Param("patientcode") String patientcode, @Param("type") String type);

    /**
     * 根据体检号查询
     *
     * @param patientcode 体检号
     */
    OtherReport getByPatientcode(@Param("patientcode") String patientcode);
}
