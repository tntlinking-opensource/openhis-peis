package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ReviewProjectMapper;
import com.center.medical.datamove.oracle.bean.model.ReviewProject;
import com.center.medical.datamove.oracle.service.ReviewProjectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ZJ复查项目表(ReviewProject)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:25
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


}


