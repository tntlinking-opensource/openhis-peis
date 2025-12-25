package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxItemscheckMapper;
import com.center.medical.bean.model.FxItemscheck;
import com.center.medical.service.FxItemscheckService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 综合分析-项目參检（健康）(FxItemscheck)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:38
 */
@Slf4j
@Service("fxItemscheckService")
@RequiredArgsConstructor
public class FxItemscheckServiceImpl extends ServiceImpl<FxItemscheckMapper, FxItemscheck> implements FxItemscheckService {

    private final FxItemscheckMapper fxItemscheckMapper;

    /**
     * 分页查询[综合分析-项目參检（健康）]列表
     *
     * @param page  分页参数
     * @param param FxItemscheck查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxItemscheck> getList(PageParam<FxItemscheck> page, FxItemscheck param) {
        return fxItemscheckMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxItemscheck getInfoById(String id) {
        return fxItemscheckMapper.getInfoById(id);
    }

    /**
     * 根据样本id查询
     * @param analyzeId
     * @return
     */
    @Override
    public List<FxItemscheck> getListBySampleId(String analyzeId) {
        List<FxItemscheck> list = fxItemscheckMapper.selectList(new QueryWrapper<FxItemscheck>()
                .eq("sample_id", analyzeId).orderByAsc("rowno"));
        return list;
    }
}

