package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.param.AddNotificationParam;
import com.center.medical.common.core.domain.entity.SysNoticeConfig;
import com.center.medical.common.core.domain.entity.SysNotification;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.SysNotificationPageParam;
import com.center.medical.system.bean.vo.SysNotificationPageVo;
import com.center.medical.system.dao.SysNoticeConfigMapper;
import com.center.medical.system.dao.SysNotificationMapper;
import com.center.medical.system.service.SysNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * 通知配置表(SysNoticeConfig)服务实现类
 *
 * @author makejava
 * @since 2025-02-26 16:42:50
 */
@Slf4j
@Service("sysNotificationService")
@RequiredArgsConstructor
public class SysNotificationServiceImpl extends ServiceImpl<SysNotificationMapper, SysNotification> implements SysNotificationService {

    private final SysNotificationMapper sysNotificationMapper;
    private final MapperFacade mapperFacade;
    private final SysNoticeConfigMapper sysNoticeConfigMapper;

    /**
     * 分页查询[通知配置表]列表
     *
     * @param page  分页参数
     * @param param SysNoticeConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysNotificationPageVo> getPage(PageParam<SysNotificationPageVo> page, SysNotificationPageParam param) {
        return sysNotificationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysNotification getInfoById(String id) {
        return sysNotificationMapper.getInfoById(id);
    }

    /**
     * 获取未读的消息
     * @param userNo
     * @return
     */
    @Override
    public Long getUnreadMsg(String userNo) {
        return sysNotificationMapper.getUnreadMsg(userNo);
    }

    /**
     * 添加消息
     */
    @Override
    public void addNotice(AddNotificationParam param) {
        SysNoticeConfig sysNoticeConfig = sysNoticeConfigMapper.getInfoById(param.getNoticeConfigId());
        if (ObjectUtils.isEmpty(sysNoticeConfig)){
            return;
        }
        SysNotification sysNotification = new SysNotification();
        sysNotification.setTarUserId(param.getTarUserId());
        sysNotification.setNoticeConfigId(param.getNoticeConfigId());
        sysNotification.setIsRead(0);
        String content = StringUtils.isNotEmpty(param.getPlaceholder()) ?
                String.format(sysNoticeConfig.getNoticeText(),param.getPlaceholder()) : sysNoticeConfig.getNoticeText();
        sysNotification.setContent(content);
        sysNotificationMapper.insert(sysNotification);
    }

    /**
     * 全部已读
     * @return
     */
    @Override
    public Boolean allRead() {
        SysNotification sysNotification = new SysNotification();
        sysNotification.setIsRead(1);
        sysNotificationMapper.update(sysNotification,
                new LambdaQueryWrapper<SysNotification>().eq(SysNotification::getTarUserId,SecurityUtils.getUserNo()));
        return Boolean.TRUE;
    }
}

