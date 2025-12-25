package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Attachment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC附件(Attachment)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:14
 */
public interface AttachmentService extends IService<Attachment> {

    /**
     * 分页查询[JC附件]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Attachment> getPage(PageParam<Attachment> page, Attachment param);


}

