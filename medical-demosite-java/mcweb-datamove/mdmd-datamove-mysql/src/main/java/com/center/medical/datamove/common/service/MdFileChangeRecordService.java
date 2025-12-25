package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFileChangeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 记录有文件改变的文件目录(MdFileChangeRecord)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:17
 */
public interface MdFileChangeRecordService extends IService<MdFileChangeRecord> {

    /**
     * 分页查询[记录有文件改变的文件目录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFileChangeRecord> getPage(PageParam<MdFileChangeRecord> page, MdFileChangeRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFileChangeRecord getInfoById(String id);

}

