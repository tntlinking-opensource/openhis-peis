package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Describe;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS科室描述、检查结果表(Describe)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:08
 */
public interface DescribeMapper extends BaseMapper<Describe> {

    /**
     * 分页查询[KS科室描述、检查结果表]列表
     *
     * @param page  分页参数
     * @param param Describe查询参数
     * @return 分页数据
     */
    IPage<Describe> getPage(PageParam<Describe> page, @Param("param") Describe param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Describe getInfoById(@Param("id") String id);

}
