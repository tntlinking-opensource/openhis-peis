package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.domain.entity.SysNotification;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.SysNotificationPageParam;
import com.center.medical.system.bean.vo.SysNotificationPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * 通知公告表 数据层
 *
 * @author 路飞
 */
public interface SysNotificationMapper extends BaseMapper<SysNotification> {

    /**
     * 分页查询[系统服务(记录系统服务种类)]列表
     *
     * @param page  分页参数
     * @param param SysServiceType查询参数
     * @return 分页数据
     */
    IPage<SysNotificationPageVo> getPage(PageParam<SysNotificationPageVo> page, @Param("param") SysNotificationPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键serviceId
     * @return 详情信息
     */
    SysNotification getInfoById(@Param("id") String id);

    /**
     * 获取未读的消息
     * @param userNo
     * @return
     */
    Long getUnreadMsg(@Param("userNo") String userNo);
}
