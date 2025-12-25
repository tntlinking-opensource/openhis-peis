package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Temporaryqueuehuaou;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (Temporaryqueuehuaou)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:18
 */
public interface TemporaryqueuehuaouMapper extends BaseMapper<Temporaryqueuehuaou> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Temporaryqueuehuaou查询参数
     * @return 分页数据
     */
    IPage<Temporaryqueuehuaou> getPage(PageParam<Temporaryqueuehuaou> page, @Param("param") Temporaryqueuehuaou param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Temporaryqueuehuaou getInfoById(@Param("id") Object id);

}
