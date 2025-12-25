package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BasMergeConclusion;
import com.center.medical.data.dao.BasMergeConclusionMapper;
import com.center.medical.data.service.BasMergeConclusionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合并结伦词中间表(BasMergeConclusion)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:37
 */
@Slf4j
@Service("basMergeConclusionService")
@RequiredArgsConstructor
public class BasMergeConclusionServiceImpl extends ServiceImpl<BasMergeConclusionMapper, BasMergeConclusion> implements BasMergeConclusionService {

    private final BasMergeConclusionMapper basMergeConclusionMapper;

    /**
     * 分页查询[合并结伦词中间表]列表
     *
     * @param page  分页参数
     * @param param BasMergeConclusion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BasMergeConclusion> getList(PageParam<BasMergeConclusion> page, BasMergeConclusion param) {
        return basMergeConclusionMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BasMergeConclusion getInfoById(String id) {
        return basMergeConclusionMapper.getInfoById(id);
    }

    /**
     * 获取历史
     *
     * @param smid
     * @param dh
     * @return
     */
    @Override
    public List<BasMergeConclusion> getHistory(String smid, int dh, String patientarchiveno) {
        return basMergeConclusionMapper.getHistory(smid, dh, patientarchiveno);
    }

    /**
     * 获取合并的名称
     *
     * @param conclusionId
     * @return
     */
    @Override
    public List<String> getMergeConbination(String conclusionId) {
        return basMergeConclusionMapper.getMergeConbination(conclusionId);
    }


}

