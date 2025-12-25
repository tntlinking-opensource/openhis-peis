package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppInfoHitsMapper;
import com.center.medical.datamove.oracle.bean.model.AppInfoHits;
import com.center.medical.datamove.oracle.service.AppInfoHitsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 小程序资讯点击记录(AppInfoHits)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:38
 */
@Slf4j
@Service("appInfoHitsService")
@RequiredArgsConstructor
public class AppInfoHitsServiceImpl extends ServiceImpl<AppInfoHitsMapper, AppInfoHits> implements AppInfoHitsService {

    private final AppInfoHitsMapper appInfoHitsMapper;

    /**
     * 分页查询[小程序资讯点击记录]列表
     *
     * @param page  分页参数
     * @param param AppInfoHits查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppInfoHits> getPage(PageParam<AppInfoHits> page, AppInfoHits param) {
        return appInfoHitsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppInfoHits getInfoById(String id) {
        return appInfoHitsMapper.getInfoById(id);
    }

}


