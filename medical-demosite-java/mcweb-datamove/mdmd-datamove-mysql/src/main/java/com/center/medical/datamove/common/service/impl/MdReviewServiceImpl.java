package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReviewMapper;
import com.center.medical.datamove.common.bean.model.MdReview;
import com.center.medical.datamove.common.service.MdReviewService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ZJ复查表(MdReview)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:19
 */
@Slf4j
@Service("mdReviewService")
@RequiredArgsConstructor
public class MdReviewServiceImpl extends ServiceImpl<MdReviewMapper, MdReview> implements MdReviewService {

    private final MdReviewMapper mdReviewMapper;

    /**
     * 分页查询[ZJ复查表]列表
     *
     * @param page  分页参数
     * @param param MdReview查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReview> getPage(PageParam<MdReview> page, MdReview param) {
        return mdReviewMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReview getInfoById(String id) {
        return mdReviewMapper.getInfoById(id);
    }

    ;

}


