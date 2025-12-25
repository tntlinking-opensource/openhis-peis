package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppReservationGroupMapper;
import com.center.medical.datamove.oracle.bean.model.AppReservationGroup;
import com.center.medical.datamove.oracle.service.AppReservationGroupService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 小程序团检预约人数(AppReservationGroup)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:55
 */
@Slf4j
@Service("appReservationGroupService")
@RequiredArgsConstructor
public class AppReservationGroupServiceImpl extends ServiceImpl<AppReservationGroupMapper, AppReservationGroup> implements AppReservationGroupService {

    private final AppReservationGroupMapper appReservationGroupMapper;

    /**
     * 分页查询[小程序团检预约人数]列表
     *
     * @param page  分页参数
     * @param param AppReservationGroup查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppReservationGroup> getPage(PageParam<AppReservationGroup> page, AppReservationGroup param) {
        return appReservationGroupMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppReservationGroup getInfoById(String id) {
        return appReservationGroupMapper.getInfoById(id);
    }

}


