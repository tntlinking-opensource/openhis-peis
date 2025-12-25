package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppNoticeMapper;
import com.center.medical.datamove.oracle.bean.model.AppNotice;
import com.center.medical.datamove.oracle.service.AppNoticeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 沃德小程序公告(AppNotice)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:46
 */
@Slf4j
@Service("appNoticeService")
@RequiredArgsConstructor
public class AppNoticeServiceImpl extends ServiceImpl<AppNoticeMapper, AppNotice> implements AppNoticeService {

    private final AppNoticeMapper appNoticeMapper;

    /**
     * 分页查询[沃德小程序公告]列表
     *
     * @param page  分页参数
     * @param param AppNotice查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppNotice> getPage(PageParam<AppNotice> page, AppNotice param) {
        return appNoticeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppNotice getInfoById(String id) {
        return appNoticeMapper.getInfoById(id);
    }

    ;

}


