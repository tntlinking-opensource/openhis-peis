package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmReceiveandsell;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户公共池领取与领取人员关联表(CrmReceiveandsell)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
public interface CrmReceiveandsellMapper extends BaseMapper<CrmReceiveandsell> {

    /**
     * 分页查询[客户公共池领取与领取人员关联表]列表
     *
     * @param page  分页参数
     * @param param CrmReceiveandsell查询参数
     * @return 分页数据
     */
    IPage<CrmReceiveandsell> getPage(PageParam<CrmReceiveandsell> page, @Param("param") CrmReceiveandsell param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmReceiveandsell getInfoById(@Param("id") String id);

}
