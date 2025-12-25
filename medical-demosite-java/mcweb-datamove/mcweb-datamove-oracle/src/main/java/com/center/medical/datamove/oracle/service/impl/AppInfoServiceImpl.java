package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppInfoMapper;
import com.center.medical.datamove.oracle.bean.model.AppInfo;
import com.center.medical.datamove.oracle.service.AppInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * APP???(AppInfo)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:37
 */
@Slf4j
@Service("appInfoService")
@RequiredArgsConstructor
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements AppInfoService {

    private final AppInfoMapper appInfoMapper;

    /**
     * 分页查询[APP???]列表
     *
     * @param page  分页参数
     * @param param AppInfo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppInfo> getPage(PageParam<AppInfo> page, AppInfo param) {
        return appInfoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppInfo getInfoById(String id) {
        return appInfoMapper.getInfoById(id);
    }

}


