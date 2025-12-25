package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxDetectionzyMapper;
import com.center.medical.bean.model.FxDetectionzy;
import com.center.medical.service.FxDetectionzyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TJ综合分析-检出人数（职业）(FxDetectionzy)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:38
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
    public IPage<FxDetectionzy> getList(PageParam<FxDetectionzy> page, FxDetectionzy param) {
        return fxDetectionzyMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxDetectionzy getInfoById(String id) {
        return fxDetectionzyMapper.getInfoById(id);
    }

}

