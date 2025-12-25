package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseZone;
import com.center.medical.data.dao.BaseZoneMapper;
import com.center.medical.data.service.BaseZoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 所属地区(BaseZone)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:48
 */
@Slf4j
@Service("baseZoneService")
@RequiredArgsConstructor
public class BaseZoneServiceImpl extends ServiceImpl<BaseZoneMapper, BaseZone> implements BaseZoneService {

    private final BaseZoneMapper baseZoneMapper;

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param BaseZone查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseZone> getList(PageParam<BaseZone> page, BaseZone param) {
        return baseZoneMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BaseZone getInfoById(String id) {
        return baseZoneMapper.getInfoById(id);
    }

    ;

}

