package com.center.medical.sync.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncFile;

import java.util.List;

/**
 * 同步文件操作(SyncFile)服务接口
 *
 * @author makejava
 * @since 2023-09-12 10:25:46
 */
public interface SyncFileService extends IService<SyncFile> {

    /**
     * 分页查询[同步文件操作]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SyncFile> getPage(PageParam<SyncFile> page, SyncFile param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SyncFile getInfoById(Long id);

    /**
     * 获取最新的一条数据
     *
     * @param imgUrl 图片地址
     * @param list   同步状态
     * @return
     */
    SyncFile getLastOne(String imgUrl, List<Integer> list);
}

