package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysConfigMapper;
import com.center.medical.datamove.common.bean.model.SysConfig;
import com.center.medical.datamove.common.service.SysConfigService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 参数配置表(SysConfig)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
@Slf4j
@Service("sysConfigService")
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    /**
     * 分页查询[参数配置表]列表
     *
     * @param page  分页参数
     * @param param SysConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysConfig> getPage(PageParam<SysConfig> page, SysConfig param) {
        return sysConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键configId
     * @return 详情信息
     */
    @Override
    public SysConfig getInfoById(Integer id) {
        return sysConfigMapper.getInfoById(id);
    }

}


