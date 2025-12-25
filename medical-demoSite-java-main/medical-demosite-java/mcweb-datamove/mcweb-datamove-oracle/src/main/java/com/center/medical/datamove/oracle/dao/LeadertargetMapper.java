package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Leadertarget;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 领导目标表(Leadertarget)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:35
 */
public interface LeadertargetMapper extends BaseMapper<Leadertarget> {

    /**
     * 分页查询[领导目标表]列表
     *
     * @param page  分页参数
     * @param param Leadertarget查询参数
     * @return 分页数据
     */
    IPage<Leadertarget> getPage(PageParam<Leadertarget> page, @Param("param") Leadertarget param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Leadertarget getInfoById(@Param("id") String id);

}
