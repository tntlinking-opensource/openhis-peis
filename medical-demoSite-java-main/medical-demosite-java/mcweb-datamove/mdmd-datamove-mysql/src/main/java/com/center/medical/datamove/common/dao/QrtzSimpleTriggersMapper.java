package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.QrtzSimpleTriggers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 简单触发器的信息表(QrtzSimpleTriggers)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
public interface QrtzSimpleTriggersMapper extends BaseMapper<QrtzSimpleTriggers> {

    /**
     * 分页查询[简单触发器的信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzSimpleTriggers查询参数
     * @return 分页数据
     */
    IPage<QrtzSimpleTriggers> getPage(PageParam<QrtzSimpleTriggers> page, @Param("param") QrtzSimpleTriggers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzSimpleTriggers getInfoById(@Param("id") String id);

}
