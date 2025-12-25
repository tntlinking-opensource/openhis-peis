package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppLogMapper;
import com.center.medical.datamove.oracle.bean.model.AppLog;
import com.center.medical.datamove.oracle.service.AppLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序操作日志(AppLog)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:45
 */
@Slf4j
@Service("appLogService")
@RequiredArgsConstructor
public class AppLogServiceImpl extends ServiceImpl<AppLogMapper, AppLog> implements AppLogService {

    private final AppLogMapper appLogMapper;

    /**
     * 分页查询[微信小程序操作日志]列表
     *
     * @param page  分页参数
     * @param param AppLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppLog> getPage(PageParam<AppLog> page, AppLog param) {
        return appLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppLog getInfoById(String id) {
        return appLogMapper.getInfoById(id);
    }

}


