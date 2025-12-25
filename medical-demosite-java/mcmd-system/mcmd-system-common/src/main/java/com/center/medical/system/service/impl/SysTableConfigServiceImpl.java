package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.system.bean.model.SysTableConfig;
import com.center.medical.system.dao.SysTableConfigMapper;
import com.center.medical.system.service.SysTableConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据表配置(SysTableConfig)服务实现类
 *
 * @author makejava
 * @since 2023-07-05 15:26:07
 */
@Slf4j
@Service("sysTableConfigService")
@RequiredArgsConstructor
public class SysTableConfigServiceImpl extends ServiceImpl<SysTableConfigMapper, SysTableConfig> implements SysTableConfigService {

    @Resource
    private SysTableConfigMapper sysTableConfigMapper;
    @Resource
    private LoadProperties loadProperties;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @Override
    public void init() {
        //将数据表配置加载进redis缓存中
        List<SysTableConfig> list = sysTableConfigMapper.selectList(new LambdaQueryWrapper<SysTableConfig>());
        list.forEach(item -> {
//                log.info("数据表配置信息DB：{}", JSONUtil.toJsonStr(item));
            RedisUtil.set(Constants.SYS_TABLE_CONFIG + item.getTableName(), item, -1);
        });
    }

    /**
     * 分页查询[数据表配置]列表
     *
     * @param page  分页参数
     * @param param SysTableConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysTableConfig> getPage(PageParam<SysTableConfig> page, SysTableConfig param) {
        return sysTableConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysTableConfig getInfoById(Integer id) {
        return sysTableConfigMapper.getInfoById(id);
    }

    /**
     * 根据表名获取数据表配置信息
     *
     * @param tableName
     * @return
     */
    @Override
    @Cacheable(cacheNames = "SysTableConfig", key = "#tableName")
    public SysTableConfig getInfoByTN(String tableName) {
        return sysTableConfigMapper.selectOne(new LambdaQueryWrapper<SysTableConfig>().eq(SysTableConfig::getTableName, tableName));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "SysTableConfig", key = "#tableName")
    })
    public void removeSysTableConfig(String tableName) {
    }

}

