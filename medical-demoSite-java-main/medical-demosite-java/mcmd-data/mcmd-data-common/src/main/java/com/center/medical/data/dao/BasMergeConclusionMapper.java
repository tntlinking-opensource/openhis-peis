package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BasMergeConclusion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合并结伦词中间表(BasMergeConclusion)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:37
 */
public interface BasMergeConclusionMapper extends BaseMapper<BasMergeConclusion> {

    /**
     * 分页查询[合并结伦词中间表]列表
     *
     * @param page  分页参数
     * @param param BasMergeConclusion查询参数
     * @return 分页数据
     */
    IPage<BasMergeConclusion> getList(PageParam<BasMergeConclusion> page, @Param("param") BasMergeConclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BasMergeConclusion getInfoById(@Param("id") String id);

    /**
     * 获取历史
     *
     * @param smid
     * @param dh
     * @param patientarchiveno
     * @return
     */
    List<BasMergeConclusion> getHistory(@Param("smid") String smid, @Param("dh") int dh, @Param("patientarchiveno") String patientarchiveno);

    /**
     * 获取合并的名称
     *
     * @param conclusionId
     * @return
     */
    List<String> getMergeConbination(@Param("conclusionid") String conclusionId);
}
