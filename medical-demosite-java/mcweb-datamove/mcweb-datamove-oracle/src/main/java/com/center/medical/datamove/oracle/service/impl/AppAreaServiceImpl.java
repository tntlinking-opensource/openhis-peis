package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppAreaMapper;
import com.center.medical.datamove.oracle.bean.model.AppArea;
import com.center.medical.datamove.oracle.service.AppAreaService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppArea)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:23
 */
@Slf4j
@Service("appAreaService")
@RequiredArgsConstructor
public class AppAreaServiceImpl extends ServiceImpl<AppAreaMapper, AppArea> implements AppAreaService {

    private final AppAreaMapper appAreaMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppArea查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppArea> getPage(PageParam<AppArea> page, AppArea param) {
        return appAreaMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppArea getInfoById(String id) {
        return appAreaMapper.getInfoById(id);
    }

}


