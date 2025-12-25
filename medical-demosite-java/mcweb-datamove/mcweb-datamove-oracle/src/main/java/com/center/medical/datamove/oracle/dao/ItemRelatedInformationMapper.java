package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ItemRelatedInformation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 第三方项目接口对接信息表(ItemRelatedInformation)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:18
 */
public interface ItemRelatedInformationMapper extends BaseMapper<ItemRelatedInformation> {

    /**
     * 分页查询[第三方项目接口对接信息表]列表
     *
     * @param page  分页参数
     * @param param ItemRelatedInformation查询参数
     * @return 分页数据
     */
    IPage<ItemRelatedInformation> getPage(PageParam<ItemRelatedInformation> page, @Param("param") ItemRelatedInformation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ItemRelatedInformation getInfoById(@Param("id") String id);

}
