package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdItemsNew;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC收费项目表(MdItemsNew)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
public interface MdItemsNewMapper extends BaseMapper<MdItemsNew> {

    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param MdItemsNew查询参数
     * @return 分页数据
     */
    IPage<MdItemsNew> getPage(PageParam<MdItemsNew> page, @Param("param") MdItemsNew param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdItemsNew getInfoById(@Param("id") String id);

}
