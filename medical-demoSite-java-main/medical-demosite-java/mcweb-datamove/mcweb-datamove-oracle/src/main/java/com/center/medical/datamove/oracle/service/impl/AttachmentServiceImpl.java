package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AttachmentMapper;
import com.center.medical.datamove.oracle.bean.model.Attachment;
import com.center.medical.datamove.oracle.service.AttachmentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC附件(Attachment)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:14
 */
@Slf4j
@Service("attachmentService")
@RequiredArgsConstructor
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    private final AttachmentMapper attachmentMapper;

    /**
     * 分页查询[JC附件]列表
     *
     * @param page  分页参数
     * @param param Attachment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Attachment> getPage(PageParam<Attachment> page, Attachment param) {
        return attachmentMapper.getPage(page, param);
    }


}


