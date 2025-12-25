package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AreaMapper;
import com.center.medical.datamove.oracle.bean.model.Area;
import com.center.medical.datamove.oracle.service.AreaService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 籍贯表(Area)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:13
 */
@Slf4j
@Service("areaService")
@RequiredArgsConstructor
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    private final AreaMapper areaMapper;

    /**
     * 分页查询[籍贯表]列表
     *
     * @param page  分页参数
     * @param param Area查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Area> getPage(PageParam<Area> page, Area param) {
        return areaMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Area getInfoById(String id) {
        return areaMapper.getInfoById(id);
    }

}


