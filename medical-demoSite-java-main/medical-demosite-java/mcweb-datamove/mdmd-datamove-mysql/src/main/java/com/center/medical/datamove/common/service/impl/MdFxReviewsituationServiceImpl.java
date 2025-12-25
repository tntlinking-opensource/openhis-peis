package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxReviewsituationMapper;
import com.center.medical.datamove.common.bean.model.MdFxReviewsituation;
import com.center.medical.datamove.common.service.MdFxReviewsituationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本次职业健康检查复查人员复查情况一览表(MdFxReviewsituation)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
@Slf4j
@Service("mdFxReviewsituationService")
@RequiredArgsConstructor
public class MdFxReviewsituationServiceImpl extends ServiceImpl<MdFxReviewsituationMapper, MdFxReviewsituation> implements MdFxReviewsituationService {

    private final MdFxReviewsituationMapper mdFxReviewsituationMapper;

    /**
     * 分页查询[本次职业健康检查复查人员复查情况一览表]列表
     *
     * @param page  分页参数
     * @param param MdFxReviewsituation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxReviewsituation> getPage(PageParam<MdFxReviewsituation> page, MdFxReviewsituation param) {
        return mdFxReviewsituationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxReviewsituation getInfoById(String id) {
        return mdFxReviewsituationMapper.getInfoById(id);
    }

}


