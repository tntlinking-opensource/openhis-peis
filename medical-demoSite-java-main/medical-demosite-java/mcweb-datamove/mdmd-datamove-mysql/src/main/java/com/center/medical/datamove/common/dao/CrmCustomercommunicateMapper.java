package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmCustomercommunicate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户沟通表(CrmCustomercommunicate)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:44:58
 */
public interface CrmCustomercommunicateMapper extends BaseMapper<CrmCustomercommunicate> {

    /**
     * 分页查询[客户沟通表]列表
     *
     * @param page  分页参数
     * @param param CrmCustomercommunicate查询参数
     * @return 分页数据
     */
    IPage<CrmCustomercommunicate> getPage(PageParam<CrmCustomercommunicate> page, @Param("param") CrmCustomercommunicate param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmCustomercommunicate getInfoById(@Param("id") String id);

}
