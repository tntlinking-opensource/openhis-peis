package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.StencilMaintain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 用于保存科室的模板（个检用）、团检的模板、对比模板(StencilMaintain)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:05
 */
public interface StencilMaintainMapper extends BaseMapper<StencilMaintain> {

    /**
     * 分页查询[用于保存科室的模板（个检用）、团检的模板、对比模板]列表
     *
     * @param page  分页参数
     * @param param StencilMaintain查询参数
     * @return 分页数据
     */
    IPage<StencilMaintain> getPage(PageParam<StencilMaintain> page, @Param("param") StencilMaintain param);


}
