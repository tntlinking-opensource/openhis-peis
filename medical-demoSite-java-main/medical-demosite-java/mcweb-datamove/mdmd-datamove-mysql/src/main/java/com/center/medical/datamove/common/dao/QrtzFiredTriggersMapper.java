package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.QrtzFiredTriggers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 已触发的触发器表(QrtzFiredTriggers)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
public interface QrtzFiredTriggersMapper extends BaseMapper<QrtzFiredTriggers> {

    /**
     * 分页查询[已触发的触发器表]列表
     *
     * @param page  分页参数
     * @param param QrtzFiredTriggers查询参数
     * @return 分页数据
     */
    IPage<QrtzFiredTriggers> getPage(PageParam<QrtzFiredTriggers> page, @Param("param") QrtzFiredTriggers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzFiredTriggers getInfoById(@Param("id") String id);

}
