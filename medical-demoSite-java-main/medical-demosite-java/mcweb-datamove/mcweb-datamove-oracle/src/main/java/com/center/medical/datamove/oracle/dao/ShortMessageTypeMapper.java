package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ShortMessageType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC短信信息分类表(ShortMessageType)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:58
 */
public interface ShortMessageTypeMapper extends BaseMapper<ShortMessageType> {

    /**
     * 分页查询[JC短信信息分类表]列表
     *
     * @param page  分页参数
     * @param param ShortMessageType查询参数
     * @return 分页数据
     */
    IPage<ShortMessageType> getPage(PageParam<ShortMessageType> page, @Param("param") ShortMessageType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ShortMessageType getInfoById(@Param("id") String id);

}
