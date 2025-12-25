package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AttachmentConfigMapper;
import com.center.medical.datamove.oracle.bean.model.AttachmentConfig;
import com.center.medical.datamove.oracle.service.AttachmentConfigService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AttachmentConfig)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:16
 */
@Slf4j
@Service("attachmentConfigService")
@RequiredArgsConstructor
public class AttachmentConfigServiceImpl extends ServiceImpl<AttachmentConfigMapper, AttachmentConfig> implements AttachmentConfigService {

    private final AttachmentConfigMapper attachmentConfigMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AttachmentConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AttachmentConfig> getPage(PageParam<AttachmentConfig> page, AttachmentConfig param) {
        return attachmentConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AttachmentConfig getInfoById(String id) {
        return attachmentConfigMapper.getInfoById(id);
    }

}


