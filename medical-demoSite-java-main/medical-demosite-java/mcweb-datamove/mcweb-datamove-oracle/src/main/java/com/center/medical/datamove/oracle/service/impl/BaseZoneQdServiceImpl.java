package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseZoneQdMapper;
import com.center.medical.datamove.oracle.bean.model.BaseZoneQd;
import com.center.medical.datamove.oracle.service.BaseZoneQdService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 所属地区(BaseZoneQd)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:42
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
    public IPage<BaseZoneQd> getPage(PageParam<BaseZoneQd> page, BaseZoneQd param) {
        return baseZoneQdMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseZoneQd getInfoById(String id) {
        return baseZoneQdMapper.getInfoById(id);
    }

}


