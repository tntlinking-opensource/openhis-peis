package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Receiveandsell;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户公共池领取与领取人员关联表(Receiveandsell)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:14
 */
public interface ReceiveandsellMapper extends BaseMapper<Receiveandsell> {

    /**
     * 分页查询[客户公共池领取与领取人员关联表]列表
     *
     * @param page  分页参数
     * @param param Receiveandsell查询参数
     * @return 分页数据
     */
    IPage<Receiveandsell> getPage(PageParam<Receiveandsell> page, @Param("param") Receiveandsell param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Receiveandsell getInfoById(@Param("id") String id);

}
