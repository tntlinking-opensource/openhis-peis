package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrInspectCharge;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目收费项目关联表(InspectCharge)数据库访问层
 *
 * @author ay
 * @since 2024-07-13 14:27:29
 */
public interface OrInspectChargeMapper extends BaseMapper<OrInspectCharge> {

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param InspectCharge查询参数
     * @return 分页数据
     */
    IPage<OrInspectCharge> getPage(PageParam<OrInspectCharge> page, @Param("param") OrInspectCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrInspectCharge getInfoById(@Param("id") String id);

}
