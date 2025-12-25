package com.center.medical.datamove.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.CrmSellpact;
import org.apache.ibatis.annotations.Param;

/**
 * 销售合同维护表(CrmSellpact)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:19:52
 */
public interface CrmSellpactMapper extends BaseMapper<CrmSellpact> {

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param CrmSellpact查询参数
     * @return 分页数据
     */
    IPage<CrmSellpact> getPage(PageParam<CrmSellpact> page, @Param("param") CrmSellpact param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSellpact getInfoById(@Param("id") String id);

}
