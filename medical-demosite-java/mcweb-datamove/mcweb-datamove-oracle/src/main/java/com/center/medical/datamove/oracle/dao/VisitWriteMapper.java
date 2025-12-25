package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.VisitWrite;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF回访记录表(VisitWrite)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:53
 */
public interface VisitWriteMapper extends BaseMapper<VisitWrite> {

    /**
     * 分页查询[KF回访记录表]列表
     *
     * @param page  分页参数
     * @param param VisitWrite查询参数
     * @return 分页数据
     */
    IPage<VisitWrite> getPage(PageParam<VisitWrite> page, @Param("param") VisitWrite param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    VisitWrite getInfoById(@Param("id") String id);

}
