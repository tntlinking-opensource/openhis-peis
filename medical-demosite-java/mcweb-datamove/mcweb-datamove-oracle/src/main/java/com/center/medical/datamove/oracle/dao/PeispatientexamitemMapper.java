package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Peispatientexamitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * LIS结果(Peispatientexamitem)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:27
 */
public interface PeispatientexamitemMapper extends BaseMapper<Peispatientexamitem> {

    /**
     * 分页查询[LIS结果]列表
     *
     * @param page  分页参数
     * @param param Peispatientexamitem查询参数
     * @return 分页数据
     */
    IPage<Peispatientexamitem> getPage(PageParam<Peispatientexamitem> page, @Param("param") Peispatientexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientexamitem getInfoById(@Param("id") String id);

}
