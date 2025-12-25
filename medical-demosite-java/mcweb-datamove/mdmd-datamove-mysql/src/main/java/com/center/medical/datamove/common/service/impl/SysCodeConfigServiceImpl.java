package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.common.bean.model.SysCodeConfig;
import com.center.medical.datamove.common.dao.SysCodeConfigMapper;
import com.center.medical.datamove.common.service.SysCodeConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 加密密钥及授权码表(CodeConfig)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
@Slf4j
@Service("sysCodeConfigService")
@RequiredArgsConstructor
public class SysCodeConfigServiceImpl extends ServiceImpl<SysCodeConfigMapper, SysCodeConfig> implements SysCodeConfigService {

    private final SysCodeConfigMapper sysCodeConfigMapper;

    /**
     * 分页查询[加密密钥及授权码表]列表
     *
     * @param page  分页参数
     * @param param SysCodeConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysCodeConfig> getPage(PageParam<SysCodeConfig> page, SysCodeConfig param) {
        return sysCodeConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysCodeConfig getInfoById(Object id) {
        return sysCodeConfigMapper.getInfoById(id);
    }

}


