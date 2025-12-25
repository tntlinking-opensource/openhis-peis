package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxCompletionMapper;
import com.center.medical.bean.model.FxCompletion;
import com.center.medical.service.FxCompletionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(FxCompletion)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:17
 */
@Slf4j
@Service("fxCompletionService")
@RequiredArgsConstructor
public class FxCompletionServiceImpl extends ServiceImpl<FxCompletionMapper, FxCompletion> implements FxCompletionService {

    private final FxCompletionMapper fxCompletionMapper;

    /**
     * 分页查询[本次职业健康检查漏检人员及漏检项目人员一览表]列表
     *
     * @param page  分页参数
     * @param param FxCompletion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxCompletion> getList(PageParam<FxCompletion> page, FxCompletion param) {
        return fxCompletionMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxCompletion getInfoById(String id) {
        return fxCompletionMapper.getInfoById(id);
    }

    /**
     * 根据样本Id查询
     * @param analyzeId
     * @return
     */
    @Override
    public List<FxCompletion> findFxCompletion(String analyzeId) {
        List<FxCompletion> list = fxCompletionMapper.selectList(new QueryWrapper<FxCompletion>()
                .eq("sample_id", analyzeId).orderByAsc("dateregister"));
        return list;
    }
}

