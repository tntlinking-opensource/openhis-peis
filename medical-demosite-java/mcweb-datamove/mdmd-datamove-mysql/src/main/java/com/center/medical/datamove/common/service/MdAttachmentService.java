package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdAttachment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC附件(MdAttachment)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
public interface MdAttachmentService extends IService<MdAttachment> {

    /**
     * 分页查询[JC附件]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdAttachment> getPage(PageParam<MdAttachment> page, MdAttachment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdAttachment getInfoById(String id);

}

