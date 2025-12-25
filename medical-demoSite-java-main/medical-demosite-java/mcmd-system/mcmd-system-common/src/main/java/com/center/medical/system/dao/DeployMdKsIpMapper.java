package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.MdKsIp;
import org.apache.ibatis.annotations.Param;

/**
 * 科室IP(MdKsIp)数据库访问层
 *
 * @author makejava
 * @since 2023-11-15 10:24:07
 */
public interface DeployMdKsIpMapper extends BaseMapper<MdKsIp> {

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
