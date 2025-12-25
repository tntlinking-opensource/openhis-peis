package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.VisitWrite;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF回访记录表(VisitWrite)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:54
 */
public interface VisitWriteMapper extends BaseMapper<VisitWrite> {

    /**
     * 分页查询[KF回访记录表]列表
     *
     * @param page  分页参数
     * @param param VisitWrite查询参数
     * @return 分页数据
     */
    IPage<VisitWrite> getList(PageParam<VisitWrite> page, @Param("param") VisitWrite param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    VisitWrite getInfoById(@Param("id") String id);

}
