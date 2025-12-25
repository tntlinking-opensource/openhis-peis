package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseAttachmentConfigMapper;
import com.center.medical.datamove.oracle.bean.model.BaseAttachmentConfig;
import com.center.medical.datamove.oracle.service.BaseAttachmentConfigService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (BaseAttachmentConfig)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:27
 */
@Slf4j
@Service("baseAttachmentConfigService")
@RequiredArgsConstructor
public class BaseAttachmentConfigServiceImpl extends ServiceImpl<BaseAttachmentConfigMapper, BaseAttachmentConfig> implements BaseAttachmentConfigService {

    private final BaseAttachmentConfigMapper baseAttachmentConfigMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BaseAttachmentConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseAttachmentConfig> getPage(PageParam<BaseAttachmentConfig> page, BaseAttachmentConfig param) {
        return baseAttachmentConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseAttachmentConfig getInfoById(String id) {
        return baseAttachmentConfigMapper.getInfoById(id);
    }

}


