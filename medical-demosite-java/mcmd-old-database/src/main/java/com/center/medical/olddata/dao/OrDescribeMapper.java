package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrDescribe;
import org.apache.ibatis.annotations.Param;

/**
 * KS科室描述、检查结果表(Describe)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 15:35:57
 */
public interface OrDescribeMapper extends BaseMapper<OrDescribe> {

    /**
     * 分页查询[KS科室描述、检查结果表]列表
     *
     * @param page  分页参数
     * @param param Describe查询参数
     * @return 分页数据
     */
    IPage<OrDescribe> getPage(PageParam<OrDescribe> page, @Param("param") OrDescribe param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrDescribe getInfoById(@Param("id") String id);

}
