package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseZoneMapper;
import com.center.medical.datamove.oracle.bean.model.BaseZone;
import com.center.medical.datamove.oracle.service.BaseZoneService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 所属地区(BaseZone)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:40
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
    public IPage<BaseZone> getPage(PageParam<BaseZone> page, BaseZone param) {
        return baseZoneMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseZone getInfoById(String id) {
        return baseZoneMapper.getInfoById(id);
    }

}


