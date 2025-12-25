package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.QrtzCronTriggers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * Cron类型的触发器表(QrtzCronTriggers)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
public interface QrtzCronTriggersMapper extends BaseMapper<QrtzCronTriggers> {

    /**
     * 分页查询[Cron类型的触发器表]列表
     *
     * @param page  分页参数
     * @param param QrtzCronTriggers查询参数
     * @return 分页数据
     */
    IPage<QrtzCronTriggers> getPage(PageParam<QrtzCronTriggers> page, @Param("param") QrtzCronTriggers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzCronTriggers getInfoById(@Param("id") String id);

}
