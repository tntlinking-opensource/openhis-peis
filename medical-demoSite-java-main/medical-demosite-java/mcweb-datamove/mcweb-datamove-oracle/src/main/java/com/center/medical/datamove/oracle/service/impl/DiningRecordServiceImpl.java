package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DiningRecordMapper;
import com.center.medical.datamove.oracle.bean.model.DiningRecord;
import com.center.medical.datamove.oracle.service.DiningRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (DiningRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:17
 */
@Slf4j
@Service("diningRecordService")
@RequiredArgsConstructor
public class DiningRecordServiceImpl extends ServiceImpl<DiningRecordMapper, DiningRecord> implements DiningRecordService {

    private final DiningRecordMapper diningRecordMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DiningRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DiningRecord> getPage(PageParam<DiningRecord> page, DiningRecord param) {
        return diningRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DiningRecord getInfoById(String id) {
        return diningRecordMapper.getInfoById(id);
    }

}


