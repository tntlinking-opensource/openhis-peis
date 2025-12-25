package com.center.medical.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.config.RsaConfig;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.CodeConfig;
import com.center.medical.system.dao.CodeConfigMapper;
import com.center.medical.system.service.CodeConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 加密密钥及授权码表(CodeConfig)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-04-04 16:10:31
 */
@Slf4j
@Service("codeConfigService")
@RequiredArgsConstructor
public class CodeConfigServiceImpl extends ServiceImpl<CodeConfigMapper, CodeConfig> implements CodeConfigService {

    private final CodeConfigMapper codeConfigMapper;

    /**
     * 分页查询[加密密钥及授权码表]列表
     *
     * @param page  分页参数
     * @param param SysCodeConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CodeConfig> getPage(PageParam<CodeConfig> page, CodeConfig param) {
        return codeConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CodeConfig getInfoById(Long id) {
        return codeConfigMapper.getInfoById(id);
    }

    /**
     * 判断键值是否有效
     *
     * @param key    键
     * @param value  值
     * @param bsFlag 业务标识
     * @return
     */
    @Override
    public Boolean isWorking(String key, String value, String bsFlag) {

        LambdaQueryWrapper<CodeConfig> wrapper = new LambdaQueryWrapper<CodeConfig>()
                .eq(CodeConfig::getStatus, 1);
        if (StringUtils.isNotBlank(value)) {
            wrapper.eq(CodeConfig::getValueText, value);
        }
        if (StringUtils.isNotBlank(key)) {
            wrapper.eq(CodeConfig::getKeyText, key);
        }
        if (StringUtils.isNotBlank(bsFlag)) {
            wrapper.eq(CodeConfig::getBsFlag, bsFlag);
        }
        return codeConfigMapper.selectCount(wrapper) == 0 ? Boolean.FALSE : Boolean.TRUE;

    }

    /**
     * 获取对象信息
     *
     * @param key    键
     * @param value  值
     * @param bsFlag 业务标识
     * @return
     */
    @Override
    public CodeConfig getObject(String key, String value, String bsFlag) {
        LambdaQueryWrapper<CodeConfig> wrapper = new LambdaQueryWrapper<CodeConfig>()
                .eq(CodeConfig::getStatus, 1);
        if (StringUtils.isNotBlank(value)) {
            wrapper.eq(CodeConfig::getValueText, value);
        }
        if (StringUtils.isNotBlank(key)) {
            wrapper.eq(CodeConfig::getKeyText, value);
        }
        if (StringUtils.isNotBlank(bsFlag)) {
            wrapper.eq(CodeConfig::getBsFlag, bsFlag);
        }
        return codeConfigMapper.selectOne(wrapper);
    }

    /**
     * 获取Rsa配置信息
     *
     * @param key
     * @param bsFlag
     * @return
     */
    @Override
    public RsaConfig getRsaConfig(String key, String bsFlag) {
        LambdaQueryWrapper<CodeConfig> wrapper = new LambdaQueryWrapper<CodeConfig>()
                .eq(CodeConfig::getStatus, 1)
                .eq(CodeConfig::getKeyText, key)
                .eq(CodeConfig::getBsFlag, bsFlag);
        CodeConfig codeConfig = codeConfigMapper.selectOne(wrapper);
        if (Objects.isNull(codeConfig) || StringUtils.isBlank(codeConfig.getValueText())) {
            return null;
        }
        return JSONUtil.toBean(codeConfig.getValueText(), RsaConfig.class);
    }

    /**
     * 获取授权信息
     * @param authCode 授权码
     * @param sourceId 第三方ID
     * @param bsFlaf 业务标识
     * @return
     */
    @Override
    public CodeConfig getInfo(String authCode, String sourceId, String bsFlaf) {
        LambdaQueryWrapper<CodeConfig> wrapper = new LambdaQueryWrapper<CodeConfig>()
                .eq(CodeConfig::getStatus, 1).eq(CodeConfig::getAuthCode, authCode);
        return codeConfigMapper.selectOne(wrapper);
    }


}

