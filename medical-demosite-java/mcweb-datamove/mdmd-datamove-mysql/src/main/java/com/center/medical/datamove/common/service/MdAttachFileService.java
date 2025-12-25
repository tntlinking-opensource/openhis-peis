package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdAttachFile;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 上传文件记录表(MdAttachFile)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
public interface MdAttachFileService extends IService<MdAttachFile> {

    /**
     * 分页查询[上传文件记录表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdAttachFile> getPage(PageParam<MdAttachFile> page, MdAttachFile param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键fileId
     * @return 详情信息
     */
    MdAttachFile getInfoById(String id);

}

