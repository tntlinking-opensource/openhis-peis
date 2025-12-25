package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdInspectCharge;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目收费项目关联表(MdInspectCharge)数据库访问层
 *
 * @author ay
 * @since 2024-07-13 13:47:00
 */
public interface MdInspectChargeMapper extends BaseMapper<MdInspectCharge> {

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param MdInspectCharge查询参数
     * @return 分页数据
     */
    IPage<MdInspectCharge> getPage(PageParam<MdInspectCharge> page, @Param("param") MdInspectCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdInspectCharge getInfoById(@Param("id") String id);

}
