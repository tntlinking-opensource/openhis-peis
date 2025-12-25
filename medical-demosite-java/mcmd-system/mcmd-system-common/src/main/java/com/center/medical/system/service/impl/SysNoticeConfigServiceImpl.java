package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.core.domain.entity.SysNoticeConfig;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.NoticeConfigPageParam;
import com.center.medical.system.bean.param.NoticeConfigSaOrUpParam;
import com.center.medical.system.dao.SysNoticeConfigMapper;
import com.center.medical.system.service.SysNoticeConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知配置表(SysNoticeConfig)服务实现类
 *
 * @author makejava
 * @since 2025-02-26 16:42:50
 */
@Slf4j
@Service("sysNoticeConfigService")
@RequiredArgsConstructor
public class SysNoticeConfigServiceImpl extends ServiceImpl<SysNoticeConfigMapper, SysNoticeConfig> implements SysNoticeConfigService {

    private final SysNoticeConfigMapper sysNoticeConfigMapper;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[通知配置表]列表
     *
     * @param page  分页参数
     * @param param SysNoticeConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysNoticeConfig> getPage(PageParam<SysNoticeConfig> page, NoticeConfigPageParam param) {
        return sysNoticeConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysNoticeConfig getInfoById(String id) {
        return sysNoticeConfigMapper.getInfoById(id);
    }

    /**
     * 添加或删除
     * @param params
     * @return
     */
    @Override
    public boolean saOrUp(NoticeConfigSaOrUpParam params) {
        SysNoticeConfig sysNoticeConfig = mapperFacade.map(params, SysNoticeConfig.class);
        sysNoticeConfig.setOperator(SecurityUtils.getUsername());
        if (StringUtils.isEmpty(params.getId())){
            sysNoticeConfig.setIsDelete(0);
        }
        return saveOrUpdate(sysNoticeConfig);
    }

    /**
     * 获取需要发送通知的用户编号
     * @param id
     * @return
     */
    @Override
    public List<String> getUserNoById(String id) {
        return sysNoticeConfigMapper.getUserNoById(id);
    }
}

