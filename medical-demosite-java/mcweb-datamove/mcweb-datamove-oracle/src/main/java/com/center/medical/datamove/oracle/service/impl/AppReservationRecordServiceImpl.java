package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppReservationRecordMapper;
import com.center.medical.datamove.oracle.bean.model.AppReservationRecord;
import com.center.medical.datamove.oracle.service.AppReservationRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 小程序预约记录（团检+个检）(AppReservationRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:00
 */
@Slf4j
@Service("appReservationRecordService")
@RequiredArgsConstructor
public class AppReservationRecordServiceImpl extends ServiceImpl<AppReservationRecordMapper, AppReservationRecord> implements AppReservationRecordService {

    private final AppReservationRecordMapper appReservationRecordMapper;

    /**
     * 分页查询[小程序预约记录（团检+个检）]列表
     *
     * @param page  分页参数
     * @param param AppReservationRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppReservationRecord> getPage(PageParam<AppReservationRecord> page, AppReservationRecord param) {
        return appReservationRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppReservationRecord getInfoById(String id) {
        return appReservationRecordMapper.getInfoById(id);
    }

}


