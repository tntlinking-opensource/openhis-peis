package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ReviewProject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * ZJ复查项目表(ReviewProject)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:25
 */
public interface ReviewProjectService extends IService<ReviewProject> {

    /**
     * 分页查询[ZJ复查项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReviewProject> getPage(PageParam<ReviewProject> page, ReviewProject param);


}

