package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientChargeMain;
import org.apache.ibatis.annotations.Param;

/**
 * (PeispatientChargeMain)数据库访问层
 *
 * @author ay
 * @since 2023-08-12 11:55:01
 */
public interface OrPeispatientChargeMainMapper extends BaseMapper<OrPeispatientChargeMain> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeMain查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientChargeMain> getPage(PageParam<OrPeispatientChargeMain> page, @Param("param") OrPeispatientChargeMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientChargeMain getInfoById(@Param("id") String id);

}
