package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ConsumptionPointsRecordMapper;
import com.center.medical.datamove.oracle.bean.model.ConsumptionPointsRecord;
import com.center.medical.datamove.oracle.service.ConsumptionPointsRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (ConsumptionPointsRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:50
 */
@Slf4j
@Service("consumptionPointsRecordService")
@RequiredArgsConstructor
public class ConsumptionPointsRecordServiceImpl extends ServiceImpl<ConsumptionPointsRecordMapper, ConsumptionPointsRecord> implements ConsumptionPointsRecordService {

    private final ConsumptionPointsRecordMapper consumptionPointsRecordMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param ConsumptionPointsRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ConsumptionPointsRecord> getPage(PageParam<ConsumptionPointsRecord> page, ConsumptionPointsRecord param) {
        return consumptionPointsRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ConsumptionPointsRecord getInfoById(String id) {
        return consumptionPointsRecordMapper.getInfoById(id);
    }

}


