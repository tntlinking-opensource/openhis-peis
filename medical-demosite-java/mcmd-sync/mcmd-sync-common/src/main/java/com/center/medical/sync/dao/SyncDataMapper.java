package com.center.medical.sync.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncData;
import org.apache.ibatis.annotations.Param;

/**
 * 同步数据操作(SyncData)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
public interface SyncDataMapper extends BaseMapper<SyncData> {

    /**
     * 分页查询[同步数据操作]列表
     *
     * @param page  分页参数
     * @param param SyncData查询参数
     * @return 分页数据
     */
    IPage<SyncData> getPage(PageParam<SyncData> page, @Param("param") SyncData param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SyncData getInfoById(@Param("id") Long id);

}
