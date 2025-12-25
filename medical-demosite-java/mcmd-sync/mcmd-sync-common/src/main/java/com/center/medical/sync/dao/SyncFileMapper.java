package com.center.medical.sync.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncFile;
import com.center.medical.sync.bean.param.SyncFileParam;
import org.apache.ibatis.annotations.Param;

/**
 * 同步文件操作(SyncImage)数据库访问层
 *
 * @author makejava
 * @since 2023-09-12 10:25:45
 */
public interface SyncFileMapper extends BaseMapper<SyncFile> {

    /**
     * 分页查询[同步文件操作]列表
     *
     * @param page  分页参数
     * @param param SyncImage查询参数
     * @return 分页数据
     */
    IPage<SyncFile> getPage(PageParam<SyncFile> page, @Param("param") SyncFile param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SyncFile getInfoById(@Param("id") Long id);

    /**
     * 获取最新的一条数据
     *
     * @param param 查询参数
     * @return
     */
    SyncFile getLastOne(@Param("param") SyncFileParam param);
}
