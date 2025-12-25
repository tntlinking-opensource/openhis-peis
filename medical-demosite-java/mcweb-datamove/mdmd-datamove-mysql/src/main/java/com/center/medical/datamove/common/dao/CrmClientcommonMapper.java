package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmClientcommon;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户公共池(CrmClientcommon)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:44:57
 */
public interface CrmClientcommonMapper extends BaseMapper<CrmClientcommon> {

    /**
     * 分页查询[客户公共池]列表
     *
     * @param page  分页参数
     * @param param CrmClientcommon查询参数
     * @return 分页数据
     */
    IPage<CrmClientcommon> getPage(PageParam<CrmClientcommon> page, @Param("param") CrmClientcommon param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmClientcommon getInfoById(@Param("id") String id);

}
