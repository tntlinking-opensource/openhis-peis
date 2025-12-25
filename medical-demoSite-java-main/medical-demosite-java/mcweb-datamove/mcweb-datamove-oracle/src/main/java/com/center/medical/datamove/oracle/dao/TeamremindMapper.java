package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Teamremind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户预检跟踪表(Teamremind)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:14
 */
public interface TeamremindMapper extends BaseMapper<Teamremind> {

    /**
     * 分页查询[客户预检跟踪表]列表
     *
     * @param page  分页参数
     * @param param Teamremind查询参数
     * @return 分页数据
     */
    IPage<Teamremind> getPage(PageParam<Teamremind> page, @Param("param") Teamremind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Teamremind getInfoById(@Param("id") String id);

}
