package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.LimitOperationMapper;
import com.center.medical.datamove.oracle.bean.model.LimitOperation;
import com.center.medical.datamove.oracle.service.LimitOperationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CW卡额度操作表(LimitOperation)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:38
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
    public IPage<LimitOperation> getPage(PageParam<LimitOperation> page, LimitOperation param) {
        return limitOperationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public LimitOperation getInfoById(String id) {
        return limitOperationMapper.getInfoById(id);
    }

    ;

}


