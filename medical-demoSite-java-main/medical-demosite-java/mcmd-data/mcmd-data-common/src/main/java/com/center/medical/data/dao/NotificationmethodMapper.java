package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Notificationmethod;
import com.center.medical.data.bean.vo.NotificationmethodVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知方式（领取方式）表(Notificationmethod)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:33
 */
public interface NotificationmethodMapper extends BaseMapper<Notificationmethod> {

    /**
     * 分页查询[通知方式（领取方式）表]列表
     *
     * @param page  分页参数
     * @param param Notificationmethod查询参数
     * @return 分页数据
     */
    IPage<Notificationmethod> getList(PageParam<Notificationmethod> page, @Param("param") Notificationmethod param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Notificationmethod getInfoById(@Param("id") String id);

    /**
     * 获取所有的通知方式
     * @return
     */
    List<NotificationmethodVo> getIssueWayData();
}
