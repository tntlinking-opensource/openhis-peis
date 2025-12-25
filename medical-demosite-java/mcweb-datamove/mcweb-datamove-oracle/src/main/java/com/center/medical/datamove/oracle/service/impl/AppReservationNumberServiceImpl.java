package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppReservationNumberMapper;
import com.center.medical.datamove.oracle.bean.model.AppReservationNumber;
import com.center.medical.datamove.oracle.service.AppReservationNumberService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 小程序预约数量上限(AppReservationNumber)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:58
 */
@Slf4j
@Service("appReservationNumberService")
@RequiredArgsConstructor
public class AppReservationNumberServiceImpl extends ServiceImpl<AppReservationNumberMapper, AppReservationNumber> implements AppReservationNumberService {

    private final AppReservationNumberMapper appReservationNumberMapper;

    /**
     * 分页查询[小程序预约数量上限]列表
     *
     * @param page  分页参数
     * @param param AppReservationNumber查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppReservationNumber> getPage(PageParam<AppReservationNumber> page, AppReservationNumber param) {
        return appReservationNumberMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppReservationNumber getInfoById(String id) {
        return appReservationNumberMapper.getInfoById(id);
    }

}


