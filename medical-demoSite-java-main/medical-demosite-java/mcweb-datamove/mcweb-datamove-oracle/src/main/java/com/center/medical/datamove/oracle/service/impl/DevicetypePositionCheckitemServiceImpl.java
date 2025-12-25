package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DevicetypePositionCheckitemMapper;
import com.center.medical.datamove.oracle.bean.model.DevicetypePositionCheckitem;
import com.center.medical.datamove.oracle.service.DevicetypePositionCheckitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (DevicetypePositionCheckitem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:11
 */
@Slf4j
@Service("devicetypePositionCheckitemService")
@RequiredArgsConstructor
public class DevicetypePositionCheckitemServiceImpl extends ServiceImpl<DevicetypePositionCheckitemMapper, DevicetypePositionCheckitem> implements DevicetypePositionCheckitemService {

    private final DevicetypePositionCheckitemMapper devicetypePositionCheckitemMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DevicetypePositionCheckitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DevicetypePositionCheckitem> getPage(PageParam<DevicetypePositionCheckitem> page, DevicetypePositionCheckitem param) {
        return devicetypePositionCheckitemMapper.getPage(page, param);
    }


}


