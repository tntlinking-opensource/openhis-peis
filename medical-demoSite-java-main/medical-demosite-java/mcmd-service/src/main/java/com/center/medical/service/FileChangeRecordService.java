package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FileChangeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 记录有文件改变的文件目录(FileChangeRecord)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:20
 */
public interface FileChangeRecordService extends IService<FileChangeRecord> {

    /**
     * 分页查询[记录有文件改变的文件目录]列表
     *
     * @param page  分页参数
     * @param param FileChangeRecord查询参数
     * @return 分页数据
     */
    IPage<FileChangeRecord> getList(PageParam<FileChangeRecord> page, FileChangeRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FileChangeRecord getInfoById(String id);

}

