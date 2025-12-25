package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxReviewsituationMapper;
import com.center.medical.bean.model.FxReviewsituation;
import com.center.medical.service.FxReviewsituationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本次职业健康检查复查人员复查情况一览表(FxReviewsituation)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:04
 */
@Slf4j
@Service("fxReviewsituationService")
@RequiredArgsConstructor
public class FxReviewsituationServiceImpl extends ServiceImpl<FxReviewsituationMapper, FxReviewsituation> implements FxReviewsituationService {

    private final FxReviewsituationMapper fxReviewsituationMapper;

    /**
     * 分页查询[本次职业健康检查复查人员复查情况一览表]列表
     *
     * @param page  分页参数
     * @param param FxReviewsituation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxReviewsituation> getList(PageParam<FxReviewsituation> page, FxReviewsituation param) {
        return fxReviewsituationMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxReviewsituation getInfoById(String id) {
        return fxReviewsituationMapper.getInfoById(id);
    }

}

