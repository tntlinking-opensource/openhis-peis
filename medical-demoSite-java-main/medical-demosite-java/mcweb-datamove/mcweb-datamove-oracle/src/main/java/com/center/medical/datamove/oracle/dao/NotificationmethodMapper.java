package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Notificationmethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 通知方式（领取方式）表(Notificationmethod)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:09
 */
public interface NotificationmethodMapper extends BaseMapper<Notificationmethod> {

    /**
     * 分页查询[通知方式（领取方式）表]列表
     *
     * @param page  分页参数
     * @param param Notificationmethod查询参数
     * @return 分页数据
     */
    IPage<Notificationmethod> getPage(PageParam<Notificationmethod> page, @Param("param") Notificationmethod param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Notificationmethod getInfoById(@Param("id") String id);

}
