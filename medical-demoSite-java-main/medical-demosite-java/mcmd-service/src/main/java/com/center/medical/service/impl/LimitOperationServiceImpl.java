package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.LimitOperationMapper;
import com.center.medical.bean.model.LimitOperation;
import com.center.medical.service.LimitOperationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CW卡额度操作表(LimitOperation)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:05
 */
@Slf4j
@Service("limitOperationService")
@RequiredArgsConstructor
public class LimitOperationServiceImpl extends ServiceImpl<LimitOperationMapper, LimitOperation> implements LimitOperationService {

    private final LimitOperationMapper limitOperationMapper;

    /**
     * 分页查询[CW卡额度操作表]列表
     *
     * @param page  分页参数
     * @param param LimitOperation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<LimitOperation> getList(PageParam<LimitOperation> page, LimitOperation param) {
        return limitOperationMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public LimitOperation getInfoById(String id) {
        return limitOperationMapper.getInfoById(id);
    }

}

