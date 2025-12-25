package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxDetectionMapper;
import com.center.medical.datamove.oracle.bean.model.FxDetection;
import com.center.medical.datamove.oracle.service.FxDetectionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本次体检异常结果检出统计(FxDetection)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:49
 */
@Slf4j
@Service("fxDetectionService")
@RequiredArgsConstructor
public class FxDetectionServiceImpl extends ServiceImpl<FxDetectionMapper, FxDetection> implements FxDetectionService {

    private final FxDetectionMapper fxDetectionMapper;

    /**
     * 分页查询[本次体检异常结果检出统计]列表
     *
     * @param page  分页参数
     * @param param FxDetection查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxDetection> getPage(PageParam<FxDetection> page, FxDetection param) {
        return fxDetectionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FxDetection getInfoById(String id) {
        return fxDetectionMapper.getInfoById(id);
    }

}


