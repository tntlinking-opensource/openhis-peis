package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.model.SysConfig;
import com.center.medical.appadmin.bean.param.ConfigPageParam;
import com.center.medical.appadmin.dao.AppConfigMapper;
import com.center.medical.appadmin.service.AppConfigService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统配置信息表(SysConfig)服务实现类
 *
 * @author ay
 * @since 2024-03-19 17:40:46
 */
@Slf4j
@Service("appConfigService")
@RequiredArgsConstructor
public class AppConfigServiceImpl extends ServiceImpl<AppConfigMapper, SysConfig> implements AppConfigService {

    private final AppConfigMapper appConfigMapper;

    /**
     * 分页查询[系统配置信息表]列表
     *
     * @param page  分页参数
     * @param param SysConfig查询参数
     * @return 分页数据
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public IPage<SysConfig> getPage(PageParam<SysConfig> page, ConfigPageParam param) {
        return appConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public SysConfig getInfoById(Long id) {
        return appConfigMapper.getInfoById(id);
    }

    /**
     * 添加或修改
     * @param sysConfig
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public Boolean saOrUp(SysConfig sysConfig) {
        if (ObjectUtils.isNotEmpty(sysConfig.getId())){
            //更新
            SysConfig sysConfig1 = appConfigMapper.getInfoById(sysConfig.getId());
            if (ObjectUtils.isEmpty(sysConfig1)){
                throw new ServiceException("该id不存在!");
            }
            appConfigMapper.updateById(sysConfig);
        }else {
            //添加
            appConfigMapper.insert(sysConfig);
        }
        return Boolean.TRUE;
    }

    /**
     * 删除配置
     * @param ids
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public Boolean deleteByIds(List<Long> ids) {
        appConfigMapper.deleteBatchIds(ids);
        return Boolean.TRUE;
    }
}

