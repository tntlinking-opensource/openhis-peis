package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmOrderConflict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 撞单记录(CrmOrderConflict)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
public interface CrmOrderConflictMapper extends BaseMapper<CrmOrderConflict> {

    /**
     * 分页查询[撞单记录]列表
     *
     * @param page  分页参数
     * @param param CrmOrderConflict查询参数
     * @return 分页数据
     */
    IPage<CrmOrderConflict> getPage(PageParam<CrmOrderConflict> page, @Param("param") CrmOrderConflict param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmOrderConflict getInfoById(@Param("id") String id);

}
