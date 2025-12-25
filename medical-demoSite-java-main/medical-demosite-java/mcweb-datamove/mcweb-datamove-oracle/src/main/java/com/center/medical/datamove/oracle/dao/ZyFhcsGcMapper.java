package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ZyFhcsGc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC工程防护(ZyFhcsGc)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:55
 */
public interface ZyFhcsGcMapper extends BaseMapper<ZyFhcsGc> {

    /**
     * 分页查询[JC工程防护]列表
     *
     * @param page  分页参数
     * @param param ZyFhcsGc查询参数
     * @return 分页数据
     */
    IPage<ZyFhcsGc> getPage(PageParam<ZyFhcsGc> page, @Param("param") ZyFhcsGc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyFhcsGc getInfoById(@Param("id") String id);

}
