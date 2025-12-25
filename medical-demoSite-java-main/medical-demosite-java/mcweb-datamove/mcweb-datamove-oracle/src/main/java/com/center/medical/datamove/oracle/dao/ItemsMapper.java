package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Items;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC收费项目表(Items)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:20
 */
public interface ItemsMapper extends BaseMapper<Items> {

    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param Items查询参数
     * @return 分页数据
     */
    IPage<Items> getPage(PageParam<Items> page, @Param("param") Items param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Items getInfoById(@Param("id") String id);

}
