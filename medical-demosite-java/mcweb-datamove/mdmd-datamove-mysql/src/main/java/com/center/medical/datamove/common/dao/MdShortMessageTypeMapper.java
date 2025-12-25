package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdShortMessageType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC短信信息分类表(MdShortMessageType)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:40
 */
public interface MdShortMessageTypeMapper extends BaseMapper<MdShortMessageType> {

    /**
     * 分页查询[JC短信信息分类表]列表
     *
     * @param page  分页参数
     * @param param MdShortMessageType查询参数
     * @return 分页数据
     */
    IPage<MdShortMessageType> getPage(PageParam<MdShortMessageType> page, @Param("param") MdShortMessageType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdShortMessageType getInfoById(@Param("id") String id);

}
