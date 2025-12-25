package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.ReviewProject;
import org.apache.ibatis.annotations.Param;

/**
 * ZJ复查项目表(ReviewProject)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:28
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

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReviewProject getInfoById(@Param("id") String id);

}
