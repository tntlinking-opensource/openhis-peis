package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PacsBasePart;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (PacsBasePart)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:39
 */
public interface PacsBasePartMapper extends BaseMapper<PacsBasePart> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsBasePart查询参数
     * @return 分页数据
     */
    IPage<PacsBasePart> getPage(PageParam<PacsBasePart> page, @Param("param") PacsBasePart param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PacsBasePart getInfoById(@Param("id") String id);

}
