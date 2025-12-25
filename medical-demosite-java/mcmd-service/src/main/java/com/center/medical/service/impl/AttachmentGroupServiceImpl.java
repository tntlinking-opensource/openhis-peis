package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.AttachmentGroup;
import com.center.medical.dao.AttachmentGroupMapper;
import com.center.medical.service.AttachmentGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 文件分组表(AttachmentGroup)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-04-06 19:06:50
 */
@Slf4j
@Service("attachmentGroupService")
@RequiredArgsConstructor
public class AttachmentGroupServiceImpl extends ServiceImpl<AttachmentGroupMapper, AttachmentGroup> implements AttachmentGroupService {

    private final AttachmentGroupMapper attachmentGroupMapper;

}

