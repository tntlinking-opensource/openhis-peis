package com.center.medical.data.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.StencilMaintain;
import org.apache.ibatis.annotations.Param;

/**
 * 模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)(StencilMaintain)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:06
 */
public interface StencilMaintainMapper extends BaseMapper<StencilMaintain> {

    /**
     * 分页查询[模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)]列表
     *
     * @param page  分页参数
     * @param param StencilMaintain查询参数
     * @return 分页数据
     */
    IPage<StencilMaintain> getList(PageParam<StencilMaintain> page, @Param("param") StencilMaintain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    StencilMaintain getInfoById(@Param("id") String id);



}
