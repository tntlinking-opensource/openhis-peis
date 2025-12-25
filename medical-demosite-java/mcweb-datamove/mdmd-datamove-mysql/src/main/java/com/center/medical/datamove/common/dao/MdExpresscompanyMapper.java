package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdExpresscompany;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 快递公司表(MdExpresscompany)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
public interface MdExpresscompanyMapper extends BaseMapper<MdExpresscompany> {

    /**
     * 分页查询[快递公司表]列表
     *
     * @param page  分页参数
     * @param param MdExpresscompany查询参数
     * @return 分页数据
     */
    IPage<MdExpresscompany> getPage(PageParam<MdExpresscompany> page, @Param("param") MdExpresscompany param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdExpresscompany getInfoById(@Param("id") String id);

}
