package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppInfoTypeMapper;
import com.center.medical.datamove.oracle.bean.model.AppInfoType;
import com.center.medical.datamove.oracle.service.AppInfoTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * APP咨询类型(AppInfoType)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:40
 */
@Slf4j
@Service("appInfoTypeService")
@RequiredArgsConstructor
public class AppInfoTypeServiceImpl extends ServiceImpl<AppInfoTypeMapper, AppInfoType> implements AppInfoTypeService {

    private final AppInfoTypeMapper appInfoTypeMapper;

    /**
     * 分页查询[APP咨询类型]列表
     *
     * @param page  分页参数
     * @param param AppInfoType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppInfoType> getPage(PageParam<AppInfoType> page, AppInfoType param) {
        return appInfoTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppInfoType getInfoById(String id) {
        return appInfoTypeMapper.getInfoById(id);
    }

}


