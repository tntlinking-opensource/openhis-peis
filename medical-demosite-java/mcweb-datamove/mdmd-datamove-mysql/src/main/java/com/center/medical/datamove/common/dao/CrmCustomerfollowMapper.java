package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmCustomerfollow;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户跟踪表(CrmCustomerfollow)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
public interface CrmCustomerfollowMapper extends BaseMapper<CrmCustomerfollow> {

    /**
     * 分页查询[客户跟踪表]列表
     *
     * @param page  分页参数
     * @param param CrmCustomerfollow查询参数
     * @return 分页数据
     */
    IPage<CrmCustomerfollow> getPage(PageParam<CrmCustomerfollow> page, @Param("param") CrmCustomerfollow param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmCustomerfollow getInfoById(@Param("id") String id);

}
