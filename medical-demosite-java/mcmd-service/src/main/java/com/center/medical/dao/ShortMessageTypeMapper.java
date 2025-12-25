package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.ShortMessageType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC短信信息分类表(ShortMessageType)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:55
 */
public interface ShortMessageTypeMapper extends BaseMapper<ShortMessageType> {

    /**
     * 分页查询[JC短信信息分类表]列表
     *
     * @param page  分页参数
     * @param param ShortMessageType查询参数
     * @return 分页数据
     */
    IPage<ShortMessageType> getList(PageParam<ShortMessageType> page, @Param("param") ShortMessageType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ShortMessageType getInfoById(@Param("id") String id);

}
