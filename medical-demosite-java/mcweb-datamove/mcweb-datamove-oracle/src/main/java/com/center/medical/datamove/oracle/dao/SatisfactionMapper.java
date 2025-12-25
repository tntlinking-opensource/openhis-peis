package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Satisfaction;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF满意度(Satisfaction)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:36
 */
public interface SatisfactionMapper extends BaseMapper<Satisfaction> {

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param param Satisfaction查询参数
     * @return 分页数据
     */
    IPage<Satisfaction> getPage(PageParam<Satisfaction> page, @Param("param") Satisfaction param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Satisfaction getInfoById(@Param("id") String id);

}
