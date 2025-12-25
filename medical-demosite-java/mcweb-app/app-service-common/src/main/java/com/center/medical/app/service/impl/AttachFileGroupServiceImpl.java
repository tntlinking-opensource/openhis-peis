package com.center.medical.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.AttachFileGroup;
import com.center.medical.app.dao.AttachFileGroupMapper;
import com.center.medical.app.service.AttachFileGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 文件分组表(AttachFileGroup)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-04-06 19:06:50
 */
@Slf4j
@Service("attachFileGroupService")
@RequiredArgsConstructor
public class AttachFileGroupServiceImpl extends ServiceImpl<AttachFileGroupMapper, AttachFileGroup> implements AttachFileGroupService {

    private final AttachFileGroupMapper attachFileGroupMapper;

}

