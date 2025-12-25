package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.DiningRecordMapper;
import com.center.medical.bean.model.DiningRecord;
import com.center.medical.service.DiningRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 就餐记录(DiningRecord)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:20
 */
@Slf4j
@Service("diningRecordService")
@RequiredArgsConstructor
public class DiningRecordServiceImpl extends ServiceImpl<DiningRecordMapper, DiningRecord> implements DiningRecordService {

    private final DiningRecordMapper diningRecordMapper;

    /**
     * 分页查询[就餐记录]列表
     *
     * @param page  分页参数
     * @param param DiningRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DiningRecord> getList(PageParam<DiningRecord> page, DiningRecord param) {
        return diningRecordMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DiningRecord getInfoById(String id) {
        return diningRecordMapper.getInfoById(id);
    }

    ;

}

