package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.QrtzTriggers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 触发器详细信息表(QrtzTriggers)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:32
 */
public interface QrtzTriggersMapper extends BaseMapper<QrtzTriggers> {

    /**
     * 分页查询[触发器详细信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzTriggers查询参数
     * @return 分页数据
     */
    IPage<QrtzTriggers> getPage(PageParam<QrtzTriggers> page, @Param("param") QrtzTriggers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzTriggers getInfoById(@Param("id") String id);

}
