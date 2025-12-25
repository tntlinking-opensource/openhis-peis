package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxDetectionzyMapper;
import com.center.medical.datamove.oracle.bean.model.FxDetectionzy;
import com.center.medical.datamove.oracle.service.FxDetectionzyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TJ综合分析-检出人数（职业）(FxDetectionzy)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:50
 */
@Slf4j
@Service("fxDetectionzyService")
@RequiredArgsConstructor
public class FxDetectionzyServiceImpl extends ServiceImpl<FxDetectionzyMapper, FxDetectionzy> implements FxDetectionzyService {

    private final FxDetectionzyMapper fxDetectionzyMapper;

    /**
     * 分页查询[TJ综合分析-检出人数（职业）]列表
     *
     * @param page  分页参数
     * @param param FxDetectionzy查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxDetectionzy> getPage(PageParam<FxDetectionzy> page, FxDetectionzy param) {
        return fxDetectionzyMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FxDetectionzy getInfoById(String id) {
        return fxDetectionzyMapper.getInfoById(id);
    }

}


