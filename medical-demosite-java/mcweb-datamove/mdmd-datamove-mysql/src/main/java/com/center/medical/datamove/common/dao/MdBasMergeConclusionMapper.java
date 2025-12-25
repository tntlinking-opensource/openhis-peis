package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBasMergeConclusion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 合并结伦词中间表(MdBasMergeConclusion)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
public interface MdBasMergeConclusionMapper extends BaseMapper<MdBasMergeConclusion> {

    /**
     * 分页查询[合并结伦词中间表]列表
     *
     * @param page  分页参数
     * @param param MdBasMergeConclusion查询参数
     * @return 分页数据
     */
    IPage<MdBasMergeConclusion> getPage(PageParam<MdBasMergeConclusion> page, @Param("param") MdBasMergeConclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasMergeConclusion getInfoById(@Param("id") String id);

}
