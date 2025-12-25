package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.config.RsaConfig;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.CodeConfig;

/**
 * 加密密钥及授权码表(CodeConfig)表服务接口
 *
 * @author 路飞船长
 * @since 2023-04-04 16:10:31
 */
public interface CodeConfigService extends IService<CodeConfig> {

    /**
     * 分页查询[加密密钥及授权码表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CodeConfig> getPage(PageParam<CodeConfig> page, CodeConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CodeConfig getInfoById(Long id);

    /**
     * 判断键值是否有效
     *
     * @param key    键
     * @param value  值
     * @param bsFlag 业务标识
     * @return
     */
    Boolean isWorking(String key, String value, String bsFlag);

    /**
     * 获取对象信息
     *
     * @param key    键
     * @param value  值
     * @param bsFlag 业务标识
     * @return
     */
    CodeConfig getObject(String key, String value, String bsFlag);

    /**
     * 获取Rsa配置信息
     *
     * @param key
     * @param bsFlag
     * @return
     */
    RsaConfig getRsaConfig(String key, String bsFlag);

    /**
     * 获取授权信息
     * @param authCode 授权码
     * @param sourceId 第三方ID
     * @param bsFlaf 业务标识
     * @return
     */
    CodeConfig getInfo(String authCode, String sourceId, String bsFlaf);
}

