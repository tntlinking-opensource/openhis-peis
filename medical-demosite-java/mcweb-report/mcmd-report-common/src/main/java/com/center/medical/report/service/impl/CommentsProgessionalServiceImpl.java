package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.CommentsProgessional;
import com.center.medical.report.dao.CommentsProgessionalMapper;
import com.center.medical.report.service.CommentsProgessionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 职业处理意见(CommentsProgessional)服务实现类：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得
 *
 * @author 路飞船长
 * @since 2022-12-08 19:27:08
 */
@Slf4j
@Service("commentsProgessionalService")
@RequiredArgsConstructor
public class CommentsProgessionalServiceImpl extends ServiceImpl<CommentsProgessionalMapper, CommentsProgessional> implements CommentsProgessionalService {

    private final CommentsProgessionalMapper commentsProgessionalMapper;

    /**
     * 分页查询[职业处理意见表：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得]列表
     *
     * @param page  分页参数
     * @param param CommentsProgessional查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CommentsProgessional> getPage(PageParam<CommentsProgessional> page, CommentsProgessional param) {
        return commentsProgessionalMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CommentsProgessional getInfoById(String id) {
        return commentsProgessionalMapper.getInfoById(id);
    }

}

