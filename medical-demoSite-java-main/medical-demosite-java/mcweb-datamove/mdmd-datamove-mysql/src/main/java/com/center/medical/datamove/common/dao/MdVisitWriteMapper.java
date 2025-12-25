package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdVisitWrite;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF回访记录表(MdVisitWrite)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:31
 */
public interface MdVisitWriteMapper extends BaseMapper<MdVisitWrite> {

    /**
     * 分页查询[KF回访记录表]列表
     *
     * @param page  分页参数
     * @param param MdVisitWrite查询参数
     * @return 分页数据
     */
    IPage<MdVisitWrite> getPage(PageParam<MdVisitWrite> page, @Param("param") MdVisitWrite param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdVisitWrite getInfoById(@Param("id") String id);

}
