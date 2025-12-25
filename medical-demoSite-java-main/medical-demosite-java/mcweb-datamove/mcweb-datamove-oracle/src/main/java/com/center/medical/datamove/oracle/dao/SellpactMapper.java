package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Sellpact;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售合同维护表(Sellpact)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:53
 */
public interface SellpactMapper extends BaseMapper<Sellpact> {

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param Sellpact查询参数
     * @return 分页数据
     */
    IPage<Sellpact> getPage(PageParam<Sellpact> page, @Param("param") Sellpact param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sellpact getInfoById(@Param("id") String id);

}
