package com.center.medical.sync.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncDataLog;
import org.apache.ibatis.annotations.Param;

/**
 * 同步数据操作记录(SyncDataLog)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:32
 */
public interface SyncDataLogMapper extends BaseMapper<SyncDataLog> {

    /**
     * 分页查询[同步数据操作记录]列表
     *
     * @param page  分页参数
     * @param param SyncDataLog查询参数
     * @return 分页数据
     */
    IPage<SyncDataLog> getPage(PageParam<SyncDataLog> page, @Param("param") SyncDataLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键logId
     * @return 详情信息
     */
    SyncDataLog getInfoById(@Param("id") Long id);

}
