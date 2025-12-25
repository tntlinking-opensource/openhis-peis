package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ReviewMapper;
import com.center.medical.datamove.oracle.bean.model.Review;
import com.center.medical.datamove.oracle.service.ReviewService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ZJ复查表(Review)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:22
 */
@Slf4j
@Service("reviewService")
@RequiredArgsConstructor
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    private final ReviewMapper reviewMapper;

    /**
     * 分页查询[ZJ复查表]列表
     *
     * @param page  分页参数
     * @param param Review查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Review> getPage(PageParam<Review> page, Review param) {
        return reviewMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Review getInfoById(String id) {
        return reviewMapper.getInfoById(id);
    }

}


