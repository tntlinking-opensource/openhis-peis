package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.InspectCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目收费项目关联表(InspectCharge)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:14
 */
public interface InspectChargeMapper extends BaseMapper<InspectCharge> {

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param InspectCharge查询参数
     * @return 分页数据
     */
    IPage<InspectCharge> getPage(PageParam<InspectCharge> page, @Param("param") InspectCharge param);


}
