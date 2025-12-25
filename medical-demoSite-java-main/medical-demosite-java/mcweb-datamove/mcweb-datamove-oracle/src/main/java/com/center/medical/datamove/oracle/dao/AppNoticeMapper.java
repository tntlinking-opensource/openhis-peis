package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppNotice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 沃德小程序公告(AppNotice)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:45
 */
public interface AppNoticeMapper extends BaseMapper<AppNotice> {

    /**
     * 分页查询[沃德小程序公告]列表
     *
     * @param page  分页参数
     * @param param AppNotice查询参数
     * @return 分页数据
     */
    IPage<AppNotice> getPage(PageParam<AppNotice> page, @Param("param") AppNotice param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppNotice getInfoById(@Param("id") String id);

}
