package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdKsIp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 科室IP(MdKsIp)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
public interface MdKsIpMapper extends BaseMapper<MdKsIp> {

    /**
     * 分页查询[科室IP]列表
     *
     * @param page  分页参数
     * @param param MdKsIp查询参数
     * @return 分页数据
     */
    IPage<MdKsIp> getPage(PageParam<MdKsIp> page, @Param("param") MdKsIp param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdKsIp getInfoById(@Param("id") String id);

}
