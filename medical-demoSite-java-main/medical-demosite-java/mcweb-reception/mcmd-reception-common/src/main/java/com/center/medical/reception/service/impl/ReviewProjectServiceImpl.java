package com.center.medical.reception.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.dao.ReviewProjectMapper;
import com.center.medical.reception.service.ReviewProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ZJ复查项目表(ReviewProject)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:28
 */
@Slf4j
@Service("reviewProjectService")
@RequiredArgsConstructor
public class ReviewProjectServiceImpl extends ServiceImpl<ReviewProjectMapper, ReviewProject> implements ReviewProjectService {

    private final ReviewProjectMapper reviewProjectMapper;

    /**
     * 分页查询[ZJ复查项目表]列表
     *
     * @param page  分页参数
     * @param param ReviewProject查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReviewProject> getPage(PageParam<ReviewProject> page, ReviewProject param) {
        return reviewProjectMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReviewProject getInfoById(String id) {
        return reviewProjectMapper.getInfoById(id);
    }

    /**
     * 根据复查表id获取复查收费项目
     * @param reviewId
     * @return
     */
    @Override
    public List<ReviewProject> getDataByPeople(String reviewId) {
        List<ReviewProject> rp = reviewProjectMapper.selectList(new QueryWrapper<ReviewProject>().eq("review_id", reviewId));
        return rp;
    }
}

