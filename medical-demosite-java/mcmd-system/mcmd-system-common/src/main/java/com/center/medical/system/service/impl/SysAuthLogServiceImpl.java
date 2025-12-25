package com.center.medical.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.enums.BsFlag;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.system.bean.dto.SysAuthInfo;
import com.center.medical.system.bean.model.BusinessSource;
import com.center.medical.system.bean.model.CodeConfig;
import com.center.medical.system.bean.model.SysAuthLog;
import com.center.medical.system.bean.param.SysAuthLogParam;
import com.center.medical.system.dao.SysAuthLogMapper;
import com.center.medical.system.service.BusinessSourceService;
import com.center.medical.system.service.CodeConfigService;
import com.center.medical.system.service.SysAuthLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 系统授权记录(SysAuthLog)服务实现类
 *
 * @author makejava
 * @since 2024-01-17 20:20:03
 */
@Slf4j
@Service("sysAuthLogService")
@RequiredArgsConstructor
public class SysAuthLogServiceImpl extends ServiceImpl<SysAuthLogMapper, SysAuthLog> implements SysAuthLogService {

    private final SysAuthLogMapper sysAuthLogMapper;
    private final MapperFacade mapperFacade;
    private final BusinessSourceService businessSourceService;
    private final CodeConfigService codeConfigService;

    /**
     * 分页查询[系统授权记录]列表
     *
     * @param page  分页参数
     * @param param SysAuthLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysAuthLog> getPage(PageParam<SysAuthLog> page, SysAuthLog param) {
        return sysAuthLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysAuthLog getInfoById(Long id) {
        return sysAuthLogMapper.getInfoById(id);
    }

    public static void main(String[] args) {
        System.out.println(BsFlag.SYS_AUTH_INFO.name());
    }

    /**
     * 生成授权信息
     *
     * @param param 参数
     * @return 加密信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysAuthLog generate(SysAuthLogParam param) {
        BusinessSource businessSource = businessSourceService.getOne(new LambdaQueryWrapper<BusinessSource>()
                .eq(BusinessSource::getSourceId, param.getInstitutionId()).eq(BusinessSource::getStatus, 1));
        if (Objects.isNull(businessSource)) {
            throw new GlobalException("未找到授权的合作机构！");
        }
        CodeConfig codeConfig = codeConfigService.getOne(new LambdaQueryWrapper<CodeConfig>().eq(CodeConfig::getBsFlag, BsFlag.SYS_AUTH_INFO.name())
                .eq(CodeConfig::getSourceId, param.getInstitutionId()).eq(CodeConfig::getStatus, 1).ge(CodeConfig::getExpiryDate, new Date()));
        if (Objects.isNull(codeConfig)) {
            log.warn("尚未生成对{}的加密信息，现执行生成操作", businessSource.getTypeName());
            codeConfig = new CodeConfig();
            codeConfig.setCodeType(0);
            codeConfig.setCreatedate(new Date());
            codeConfig.setBsFlag(BsFlag.SYS_AUTH_INFO.name());
            codeConfig.setSourceId(param.getInstitutionId());
            codeConfig.setExpiryDate(DateUtil.parseDate("2099-12-31 23:59:59"));
            codeConfig.setAuthCode(getAuthCode(16, 1));
            codeConfig.setRemark(param.getRemark()+"系统授权RSA非对称加密配置");
            Map<String, String> keys = RSAUtil.createKeys(1024);
            codeConfig.setKeyText(keys.get("publicKey"));
            codeConfig.setValueText(keys.get("privateKey"));
            codeConfig.setStatus(param.getStatus());
            codeConfigService.save(codeConfig);
        }

        //创建授权加密对象
        SysAuthInfo sysAuthInfo = mapperFacade.map(param, SysAuthInfo.class);
        sysAuthInfo.setExpired(Boolean.FALSE);
        sysAuthInfo.setAuthCode(codeConfig.getAuthCode());
        log.info("授权信息：{}", sysAuthInfo);

        //生成授权记录
        SysAuthLog sysAuthLog = mapperFacade.map(param, SysAuthLog.class);
        sysAuthLog.setAuthCode(codeConfig.getAuthCode());
        sysAuthLog.setAuthValue(RSAUtil.publicEncrypt(JSONUtil.toJsonStr(sysAuthInfo), codeConfig.getKeyText()));
        sysAuthLogMapper.insert(sysAuthLog);
        return sysAuthLog;
    }

    /**
     * 生成授权码
     * @param len 授权码长度
     * @param flag
     * @return
     */
    private String getAuthCode(int len, int flag) {
        String code = RandomUtil.randomString(len).toUpperCase();
        switch (flag) {
            case 1:
                if (codeConfigService.count(new LambdaQueryWrapper<CodeConfig>().eq(CodeConfig::getAuthCode, code)) > 0) {
                    code = getAuthCode(len, 1);
                }
                break;
            case 2:
                if (sysAuthLogMapper.selectCount(new LambdaQueryWrapper<SysAuthLog>().eq(SysAuthLog::getAuthCode, code)) > 0) {
                    code = getAuthCode(len, 2);
                }
                break;
            default:

        }
        return code;
    }

}

