package com.center.medical.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ExamtypeOpenConfig;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.config.OrderConfig;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.UserConstants;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.core.text.Convert;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.redis.RedisCache;
import com.center.medical.system.bean.dto.SysJobDto;
import com.center.medical.system.dao.SysConfigMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 参数配置 服务层实现
 *
 * @author 路飞
 */
@Slf4j
@Service
public class SysConfigServiceImpl implements ISysConfigService {
    @Resource
    private SysConfigMapper sysConfigMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private LoadProperties loadProperties;


    /**
     * 项目启动时，初始化参数到缓存
     */
    @Override
    public void init() {
        loadingConfigCache();
    }

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    @DataSource(DataSourceType.MASTER)
    public SysConfig selectConfigById(Long configId) {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return sysConfigMapper.selectConfig(config);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param key 参数key
     * @return 参数键值
     */
    @Override
    @Cacheable(cacheNames = "SysConfigObjectStr", key = "#key")
    public String selectConfigByKey(String key) {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(key)));
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(key);
        SysConfig retConfig = sysConfigMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig)) {
            redisCache.setCacheObject(getCacheKey(key), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param key   参数键名
     * @param clazz 返回值类型
     * @param <T>
     * @return
     */
    @Override
    public <T> T getSysConfigObject(String key, Class<T> clazz) {
//        log.info("SysConfigObject:{}", key);
        String configValue = selectConfigByKey(key);
//        log.info("SysConfigObject.configValue:{}", configValue);
        if (StringUtils.isBlank(configValue)) {
            return null;
        }

        if (Objects.equals("java.lang.String", clazz.getName())) {
            return (T) configValue;
        } else {
            return JSONUtil.toBean(configValue, clazz);
        }
    }

    /**
     * 获取域名配置信息
     *
     * @return Domain
     */
    @Override
    public Domain getDomain() {
        String cv = selectConfigByKey(Constants.DOMAIN_CONFIG);
        return JSONUtil.toBean(cv, Domain.class);
    }

    /**
     * 订单相关的配置信息
     *
     * @return OrderConfig
     */
    @Override
    public OrderConfig getOrderConfig() {
        String cv = selectConfigByKey(Constants.ORDER_CONFIG);
        return JSONUtil.toBean(cv, OrderConfig.class);
    }

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    @Override
    public boolean selectCaptchaEnabled() {
        String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");
        if (StringUtils.isEmpty(captchaEnabled)) {
            return true;
        }
        return Convert.toBool(captchaEnabled);
    }

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<SysConfig> selectConfigList(SysConfig config) {
        return sysConfigMapper.selectConfigList(config);
    }

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfig config) {
        int row = sysConfigMapper.insertConfig(config);
        if (row > 0) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config) {
        int row = sysConfigMapper.updateConfig(config);
        if (row > 0) {
            redisCache.deleteObject("SysConfigObjectStr::" + config.getConfigKey());
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     */
    @Override
    public void deleteConfigByIds(Long[] configIds) {
        for (Long configId : configIds) {
            SysConfig config = selectConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
                throw new ServiceException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
            sysConfigMapper.deleteConfigById(configId);
            redisCache.deleteObject(getCacheKey(config.getConfigKey()));
        }
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache() {
        List<SysConfig> configsList = sysConfigMapper.selectConfigList(new SysConfig());
        for (SysConfig config : configsList) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * 清空参数缓存数据
     */
    @Override
    public void clearConfigCache() {
        Collection<String> keys = redisCache.keys(CacheConstants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache() {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = sysConfigMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 获取合同编号序列号
     */
    @Override
    public String getContractNum() {
        String value = sysConfigMapper.getContractNum();
        int num = Integer.parseInt(value) + 1;
        sysConfigMapper.updateContractNum();
        if (num < 10) {
            value = "0000" + value;
        } else if (num < 100) {
            value = "000" + value;
        } else if (num < 1000) {
            value = "00" + value;
        } else if (num < 10000) {
            value = "0" + value;
        }
        return value;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }

    /**
     * 通过key获取配置
     *
     * @param configKey
     * @return
     */
    @Override
    public SysConfig getConfigByKey(String configKey) {
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        return sysConfigMapper.selectConfig(config);
    }

    /**
     * 通过定时任务ID获取定时任务信息
     *
     * @param jobId
     * @return
     */
    @Override
    public SysJobDto getSysJobById(Long jobId) {
        return sysConfigMapper.getSysJobById(jobId);
    }


    /**
     * 判断定时任务是否允许该IP执行
     *
     * @param jobId 定时任务ID
     * @param ips   待校验的IP
     * @return
     */
    @Override
    public Boolean sysJobAuthIp(Long jobId, List<String> ips) {
        SysJobDto job = getSysJobById(jobId);
        Boolean isAuth = Boolean.FALSE;
        for (String ip : ips) {
            if (Objects.nonNull(job) && (StringUtils.isBlank(job.getRemark()) || job.getRemark().contains(ip))) {
                isAuth = Boolean.TRUE;
                break;
            }
        }
        log.info("当前服务器的IP：{}，是否允许执行{}：{}，授权IP：{}", ips, job.getInvokeTarget(), isAuth, job.getRemark());
        return isAuth;
    }

    /**
     * 判断该分中心可以登记该体检类型的
     * @param idExamtype
     * @return
     */
    @Override
    public Boolean getExamtypeOpen(String idExamtype) {
        ExamtypeOpenConfig examtypeOpenConfig = getSysConfigObject(Constants.EXAMTYPE_OPEN_CONFIG, ExamtypeOpenConfig.class);
        if (ObjectUtils.isEmpty(examtypeOpenConfig)){
            return Boolean.TRUE;
        }
        switch (idExamtype) {
            case "0":
                return examtypeOpenConfig.getHealth();
            case "1":
                return examtypeOpenConfig.getOccupation();
            case "2":
                return examtypeOpenConfig.getComprehensive();
            case "3":
                return examtypeOpenConfig.getReview();
            default:
                return false;
        }
    }

    /**
     * 是否开启老系统查询
     * @return
     */
    @Override
    public Boolean oldSystemOpen() {
        ExamtypeOpenConfig examtypeOpenConfig = getSysConfigObject(Constants.EXAMTYPE_OPEN_CONFIG, ExamtypeOpenConfig.class);
        if (ObjectUtils.isEmpty(examtypeOpenConfig) || ObjectUtils.isEmpty(examtypeOpenConfig.getSearchOldSystems())){
            return Boolean.FALSE;
        }
        return examtypeOpenConfig.getSearchOldSystems();
    }
}
