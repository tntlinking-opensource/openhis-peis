package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBaseAttachmentConfigMapper;
import com.center.medical.datamove.common.bean.model.MdBaseAttachmentConfig;
import com.center.medical.datamove.common.service.MdBaseAttachmentConfigService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础附件配置(MdBaseAttachmentConfig)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
@Slf4j
@Service("mdBaseAttachmentConfigService")
@RequiredArgsConstructor
public class MdBaseAttachmentConfigServiceImpl extends ServiceImpl<MdBaseAttachmentConfigMapper, MdBaseAttachmentConfig> implements MdBaseAttachmentConfigService {

    private final MdBaseAttachmentConfigMapper mdBaseAttachmentConfigMapper;

    /**
     * 分页查询[基础附件配置]列表
     *
     * @param page  分页参数
     * @param param MdBaseAttachmentConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBaseAttachmentConfig> getPage(PageParam<MdBaseAttachmentConfig> page, MdBaseAttachmentConfig param) {
        return mdBaseAttachmentConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBaseAttachmentConfig getInfoById(String id) {
        return mdBaseAttachmentConfigMapper.getInfoById(id);
    }

}


