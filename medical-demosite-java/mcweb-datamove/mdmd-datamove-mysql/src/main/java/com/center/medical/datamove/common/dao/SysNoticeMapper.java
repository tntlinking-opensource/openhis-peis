package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysNotice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 通知公告表(SysNotice)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 分页查询[通知公告表]列表
     *
     * @param page  分页参数
     * @param param SysNotice查询参数
     * @return 分页数据
     */
    IPage<SysNotice> getPage(PageParam<SysNotice> page, @Param("param") SysNotice param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键noticeId
     * @return 详情信息
     */
    SysNotice getInfoById(@Param("id") Integer id);

}
