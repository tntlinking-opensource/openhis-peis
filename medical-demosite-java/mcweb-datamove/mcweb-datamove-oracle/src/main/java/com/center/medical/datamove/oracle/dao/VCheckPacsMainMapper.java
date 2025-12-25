package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.VCheckPacsMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (VCheckPacsMain)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:49
 */
public interface VCheckPacsMainMapper extends BaseMapper<VCheckPacsMain> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param VCheckPacsMain查询参数
     * @return 分页数据
     */
    IPage<VCheckPacsMain> getPage(PageParam<VCheckPacsMain> page, @Param("param") VCheckPacsMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键checkreqid
     * @return 详情信息
     */
    VCheckPacsMain getInfoById(@Param("id") Object id);

}
