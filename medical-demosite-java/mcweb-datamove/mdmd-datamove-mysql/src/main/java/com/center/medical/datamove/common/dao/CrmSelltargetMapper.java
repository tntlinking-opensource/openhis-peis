package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmSelltarget;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * XS销售目标(CrmSelltarget)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:02
 */
public interface CrmSelltargetMapper extends BaseMapper<CrmSelltarget> {

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param param CrmSelltarget查询参数
     * @return 分页数据
     */
    IPage<CrmSelltarget> getPage(PageParam<CrmSelltarget> page, @Param("param") CrmSelltarget param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSelltarget getInfoById(@Param("id") String id);

}
