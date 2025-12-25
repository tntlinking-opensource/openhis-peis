package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ReviewProject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * ZJ复查项目表(ReviewProject)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:24
 */
public interface ReviewProjectMapper extends BaseMapper<ReviewProject> {

    /**
     * 分页查询[ZJ复查项目表]列表
     *
     * @param page  分页参数
     * @param param ReviewProject查询参数
     * @return 分页数据
     */
    IPage<ReviewProject> getPage(PageParam<ReviewProject> page, @Param("param") ReviewProject param);


}
