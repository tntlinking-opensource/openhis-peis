package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseZoneQd;
import com.center.medical.data.dao.BaseZoneQdMapper;
import com.center.medical.data.service.BaseZoneQdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 所属地区(BaseZoneQd)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
@Slf4j
@Service("baseZoneQdService")
@RequiredArgsConstructor
public class BaseZoneQdServiceImpl extends ServiceImpl<BaseZoneQdMapper, BaseZoneQd> implements BaseZoneQdService {

    private final BaseZoneQdMapper baseZoneQdMapper;

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param BaseZoneQd查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseZoneQd> getList(PageParam<BaseZoneQd> page, BaseZoneQd param) {
        return baseZoneQdMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BaseZoneQd getInfoById(String id) {
        return baseZoneQdMapper.getInfoById(id);
    }

}

