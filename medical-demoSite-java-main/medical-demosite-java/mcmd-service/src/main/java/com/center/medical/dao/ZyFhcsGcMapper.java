package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.ZyFhcsGc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC工程防护(ZyFhcsGc)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:53
 */
public interface ZyFhcsGcMapper extends BaseMapper<ZyFhcsGc> {

    /**
     * 分页查询[JC工程防护]列表
     *
     * @param page  分页参数
     * @param param ZyFhcsGc查询参数
     * @return 分页数据
     */
    IPage<ZyFhcsGc> getList(PageParam<ZyFhcsGc> page, @Param("param") ZyFhcsGc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ZyFhcsGc getInfoById(@Param("id") String id);

}
