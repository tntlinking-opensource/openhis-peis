package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysNoticeMapper;
import com.center.medical.datamove.common.bean.model.SysNotice;
import com.center.medical.datamove.common.service.SysNoticeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 通知公告表(SysNotice)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
@Slf4j
@Service("sysNoticeService")
@RequiredArgsConstructor
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    private final SysNoticeMapper sysNoticeMapper;

    /**
     * 分页查询[通知公告表]列表
     *
     * @param page  分页参数
     * @param param SysNotice查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysNotice> getPage(PageParam<SysNotice> page, SysNotice param) {
        return sysNoticeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键noticeId
     * @return 详情信息
     */
    @Override
    public SysNotice getInfoById(Integer id) {
        return sysNoticeMapper.getInfoById(id);
    }

}


