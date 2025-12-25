package com.center.medical.sync.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncDataType;
import org.apache.ibatis.annotations.Param;

/**
 * 同步数据类型(SyncDataType)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:34
 */
public interface SyncDataTypeMapper extends BaseMapper<SyncDataType> {

    /**
     * 分页查询[同步数据类型]列表
     *
     * @param page  分页参数
     * @param param SyncDataType查询参数
     * @return 分页数据
     */
    IPage<SyncDataType> getPage(PageParam<SyncDataType> page, @Param("param") SyncDataType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    SyncDataType getInfoById(@Param("id") Integer id);

    void updateDiy(@Param("item") SyncDataType syncDataType);

    void updateALL();
}
