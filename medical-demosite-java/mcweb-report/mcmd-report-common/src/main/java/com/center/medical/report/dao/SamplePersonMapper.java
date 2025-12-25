package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.report.bean.model.SamplePerson;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.param.LoadSampleParam;
import com.center.medical.bean.vo.SamplePersonVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TJ团检报告人员样本表(SamplePerson)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:17
 */
public interface SamplePersonMapper extends BaseMapper<SamplePerson> {

    /**
     * 分页查询[TJ团检报告人员样本表]列表
     *
     * @param page  分页参数
     * @param param SamplePerson查询参数
     * @return 分页数据
     */
    IPage<SamplePerson> getList(PageParam<SamplePerson> page, @Param("param") SamplePerson param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SamplePerson getInfoById(@Param("id") String id);

    IPage<SamplePersonVo> loadSamplePersonData(PageParam<SamplePersonVo> page , @Param("param") LoadSampleParam param);

    void deleteByCriteria(@Param("reportId") String reportId,@Param("groupId") String groupId);

    List<SamplePerson> getListByBallCheckId(@Param("id") String id);
}
