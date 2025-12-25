package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxDetectionMapper;
import com.center.medical.bean.model.FxDetection;
import com.center.medical.service.FxDetectionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 综合分析-检出统计、团体小结（健康）(FxDetection)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
@Slf4j
@Service("fxDetectionService")
@RequiredArgsConstructor
public class FxDetectionServiceImpl extends ServiceImpl<FxDetectionMapper, FxDetection> implements FxDetectionService {

    private final FxDetectionMapper fxDetectionMapper;

    /**
     * 分页查询[综合分析-检出统计、团体小结（健康）]列表
     *
     * @param page  分页参数
     * @param param FxDetection查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxDetection> getList(PageParam<FxDetection> page, FxDetection param) {
        return fxDetectionMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxDetection getInfoById(String id) {
        return fxDetectionMapper.getInfoById(id);
    }


    /**
     * 通过检验id查询综合分析
     * @param analyzeId
     * @return
     */
    @Override
    public List<FxDetection> findFxDetection(String analyzeId) {
        QueryWrapper<FxDetection> criteria = new QueryWrapper<>();
        criteria.eq("sample_id", analyzeId);
        criteria.orderByAsc("report_sort");
        return fxDetectionMapper.selectList(criteria);
    }
}

