/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.common.bean.*;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.util.Json;
import com.center.medical.app.dao.SysConfigMapper;
import com.center.medical.app.service.SysConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lgh
 */
@Slf4j
@Service("sysConfigService")
@AllArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "SysConfigObject", key = "#key"),
            @CacheEvict(cacheNames = "SysConfig", key = "#key")
    })
    public void updateValueByKey(String key, String value) {
        sysConfigMapper.updateValueByKey(key, value);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sysConfigMapper.deleteBatch(ids);
    }

    @Override
    @Cacheable(cacheNames = "SysConfig", key = "#key")
    public String getValue(String key) {
        SysConfig config = sysConfigMapper.queryByKey(key);
        return config == null ? null : config.getParamValue();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "SysConfigObject", key = "#key"),
            @CacheEvict(cacheNames = "SysConfig", key = "#key")
    })
    public void removeSysConfig(String key) {
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> saveDeliveryConfig(SysConfig sysConfig) {
        // 失效的缓存keys
        List<String> invalidKeys = new ArrayList<>();
        SwitchBaseModel switchBaseModel = Json.parseObject(sysConfig.getParamValue(), SwitchBaseModel.class);
        if (BooleanUtil.isTrue(switchBaseModel.getIsOpen())) {
            // 关闭其他物流查询配置
            if (!Objects.equals(sysConfig.getParamKey(), Constant.ALI_QUICK_CONFIG)) {
                // 关闭阿里快递查询
                this.closeSwitch(Constant.ALI_QUICK_CONFIG, QuickAli.class, invalidKeys);
            }
            if (!Objects.equals(sysConfig.getParamKey(), Constant.QUICK100_CONFIG)) {
                // 关闭快递100查询
                this.closeSwitch(Constant.QUICK100_CONFIG, Quick100.class, invalidKeys);
            }
            if (!Objects.equals(sysConfig.getParamKey(), Constant.QUICKBIRD_CONFIG)) {
                // 关闭快递鸟查询
                this.closeSwitch(Constant.QUICKBIRD_CONFIG, QuickBird.class, invalidKeys);
            }
            // 如果有新增的物流查询配置，在这里补上关闭的代码
        }
        // 保存当前物流查询配置
        this.saveOrUpdateSysConfigByKey(sysConfig);
        invalidKeys.add(sysConfig.getParamKey());
        return invalidKeys;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> saveOssConfig(SysConfig sysConfig) {
        // 失效的缓存keys
        List<String> invalidKeys = new ArrayList<>();
        SwitchBaseModel switchBaseModel = Json.parseObject(sysConfig.getParamValue(), SwitchBaseModel.class);
        if (Objects.isNull(switchBaseModel)) {
            return new ArrayList<>();
        }
        if (BooleanUtil.isTrue(switchBaseModel.getIsOpen())) {
            // 关闭其他文件上传配置
            if (!Objects.equals(sysConfig.getParamKey(), Constant.ALI_OSS_CONFIG)) {
                // 关闭阿里文件上传
                this.closeSwitch(Constant.ALI_OSS_CONFIG, AliOss.class, invalidKeys);
            }
            if (!Objects.equals(sysConfig.getParamKey(), Constant.Q_CLOUD_CONFIG)) {
                // 关闭腾讯OSS文件上传
                this.closeSwitch(Constant.Q_CLOUD_CONFIG, QCloud.class, invalidKeys);
            }
            if (!Objects.equals(sysConfig.getParamKey(), Constant.MINIO_OSS_CONFIG)) {
                // 关闭Minio文件上传
                this.closeSwitch(Constant.MINIO_OSS_CONFIG, Minio.class, invalidKeys);
            }
            if (!Objects.equals(sysConfig.getParamKey(), Constant.HUAWEI_OBS_CONFIG)) {
                // 关闭华为文件上传
                this.closeSwitch(Constant.HUAWEI_OBS_CONFIG, HuaWeiOss.class, invalidKeys);
            }
            if (!Objects.equals(sysConfig.getParamKey(), Constant.QINIU_CONFIG)) {
                // 关闭七牛云文件上传
                this.closeSwitch(Constant.QINIU_CONFIG, Qiniu.class, invalidKeys);
            }
            // 如果有新增的文件上传配置，在这里补上关闭的代码
        }
        // 保存当前文件上传配置
        this.saveOrUpdateSysConfigByKey(sysConfig);
        invalidKeys.add(sysConfig.getParamKey());
        return invalidKeys;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "SysConfigObject", key = "#sysConfig.paramKey"),
            @CacheEvict(cacheNames = "SysConfig", key = "#sysConfig.paramKey")
    })
    public void saveOrUpdateSysConfigByKey(SysConfig sysConfig) {
        Long count = sysConfigMapper.selectCount(Wrappers.lambdaQuery(SysConfig.class)
                .eq(SysConfig::getParamKey, sysConfig.getParamKey())
        );
        if (count > 0) {
            sysConfigMapper.updateValueByKey(sysConfig.getParamKey(), sysConfig.getParamValue());
        } else {
            sysConfig.setId(null);
            sysConfigMapper.insert(sysConfig);
        }
    }

    /**
     * 关闭激活开关
     *
     * @param key         配置项key
     * @param clazz       继承了SwitchBaseModel的类
     * @param invalidKeys 缓存失效的配置集合
     * @param <T>
     */
    public <T> void closeSwitch(String key, Class<T> clazz, List<String> invalidKeys) {
        if (!SwitchBaseModel.class.isAssignableFrom(clazz)) {
            // 不是配置开关属性类的子类不能调用该方法
            return;
        }
        T valueObj = this.getSysConfigObject(key, clazz);
        if (Objects.isNull(valueObj)) {
            // 当前配置在数据库不存在
            return;
        }
        try {
            PropertyDescriptor pd = new PropertyDescriptor("isOpen", clazz);
            Method readMethod = pd.getReadMethod();
            if (BooleanUtil.isFalse((Boolean) readMethod.invoke(valueObj))) {
                // isOpen已经是关闭状态，直接返回
                return;
            }
            Method writeMethod = pd.getWriteMethod();
            // 关闭当前配置
            writeMethod.invoke(valueObj, false);
        } catch (Exception e) {
            log.error(clazz + "类配置存在问题: ", e);
            return;
        }
        String str = Json.toJsonString(valueObj);
        sysConfigMapper.updateValueByKey(key, str);
        invalidKeys.add(key);
    }

    @Override
    @Cacheable(cacheNames = "SysConfigObject", key = "#key")
    public <T> T getSysConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StrUtil.isBlank(value)) {
            return null;
        }

        if (Objects.equals("java.lang.String", clazz.getName())) {
            return (T) value;
        } else {
            return Json.parseObject(value, clazz);
        }
    }
}
