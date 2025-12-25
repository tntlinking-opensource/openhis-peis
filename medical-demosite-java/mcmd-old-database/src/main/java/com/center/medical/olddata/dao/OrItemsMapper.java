package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrItems;
import org.apache.ibatis.annotations.Param;

/**
 * JC收费项目表(Items)数据库访问层
 *
 * @author ay
 * @since 2024-07-13 14:27:28
 */
public interface OrItemsMapper extends BaseMapper<OrItems> {

    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param Items查询参数
     * @return 分页数据
     */
    IPage<OrItems> getPage(PageParam<OrItems> page, @Param("param") OrItems param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrItems getInfoById(@Param("id") String id);

}
