package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppVersionMapper;
import com.center.medical.datamove.oracle.bean.model.AppVersion;
import com.center.medical.datamove.oracle.service.AppVersionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppVersion)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:11
 */
@Slf4j
@Service("appVersionService")
@RequiredArgsConstructor
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersion> implements AppVersionService {

    private final AppVersionMapper appVersionMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppVersion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppVersion> getPage(PageParam<AppVersion> page, AppVersion param) {
        return appVersionMapper.getPage(page, param);
    }


}


