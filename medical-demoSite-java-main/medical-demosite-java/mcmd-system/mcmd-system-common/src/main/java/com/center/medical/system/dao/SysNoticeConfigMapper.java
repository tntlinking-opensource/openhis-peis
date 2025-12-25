package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.domain.entity.SysNoticeConfig;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.NoticeConfigPageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知公告表 数据层
 *
 * @author 路飞
 */
public interface SysNoticeConfigMapper extends BaseMapper<SysNoticeConfig> {

    /**
     * 分页查询[系统服务(记录系统服务种类)]列表
     *
     * @param page  分页参数
     * @param param SysServiceType查询参数
     * @return 分页数据
     */
    IPage<SysNoticeConfig> getPage(PageParam<SysNoticeConfig> page, @Param("param") NoticeConfigPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键serviceId
     * @return 详情信息
     */
    SysNoticeConfig getInfoById(@Param("id") String id);

    /**
     * 获取需要发送通知的用户编号
     * @param id
     * @return
     */
    List<String> getUserNoById(@Param("id") String id);
}
