package com.center.medical.system.service;

import com.center.medical.common.config.Domain;
import com.center.medical.common.config.OrderConfig;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.system.bean.dto.SysJobDto;

import java.util.List;

/**
 * 参数配置 服务层
 *
 * @author 路飞
 */
public interface ISysConfigService {

    void init();

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    public SysConfig selectConfigById(Long configId);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey);


    /**
     * 根据键名查询参数配置信息
     *
     * @param key   参数键名
     * @param clazz 返回值类型
     * @param <T>
     * @return
     */
    <T> T getSysConfigObject(String key, Class<T> clazz);

    /**
     * 获取域名配置信息
     *
     * @return Domain
     */
    Domain getDomain();

    /**
     * 订单相关的配置信息
     *
     * @return OrderConfig
     */
    OrderConfig getOrderConfig();

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    public boolean selectCaptchaEnabled();

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    public List<SysConfig> selectConfigList(SysConfig config);

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    public int insertConfig(SysConfig config);

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    public int updateConfig(SysConfig config);

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     */
    public void deleteConfigByIds(Long[] configIds);

    /**
     * 加载参数缓存数据
     */
    public void loadingConfigCache();

    /**
     * 清空参数缓存数据
     */
    public void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
    public void resetConfigCache();

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数信息
     * @return 结果
     */
    public String checkConfigKeyUnique(SysConfig config);

    /**
     * 获取合同编号序列号
     */
    public String getContractNum();


    /**
     * 通过key获取配置
     *
     * @param configKey
     * @return
     */
    SysConfig getConfigByKey(String configKey);

    /**
     * 通过定时任务ID获取定时任务信息
     *
     * @param jobId
     * @return
     */
    SysJobDto getSysJobById(Long jobId);

    /**
     * 判断定时任务是否允许该IP执行
     *
     * @param jobId 定时任务ID
     * @param ips   待校验的IP
     * @return
     */
    Boolean sysJobAuthIp(Long jobId, List<String> ips);

    /**
     * 判断该分中心可以登记该体检类型的
     * @param idExamtype
     * @return
     */
    Boolean getExamtypeOpen(String idExamtype);


    /**
     * 是否开启老系统查询
     * @param
     * @return
     */
    Boolean oldSystemOpen();
}
