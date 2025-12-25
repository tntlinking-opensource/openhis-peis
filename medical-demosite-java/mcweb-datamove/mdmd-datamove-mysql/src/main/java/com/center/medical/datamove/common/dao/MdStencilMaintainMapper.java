package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdStencilMaintain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)(MdStencilMaintain)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:47
 */
public interface MdStencilMaintainMapper extends BaseMapper<MdStencilMaintain> {

    /**
     * 分页查询[模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)]列表
     *
     * @param page  分页参数
     * @param param MdStencilMaintain查询参数
     * @return 分页数据
     */
    IPage<MdStencilMaintain> getPage(PageParam<MdStencilMaintain> page, @Param("param") MdStencilMaintain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdStencilMaintain getInfoById(@Param("id") String id);

}
