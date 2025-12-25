package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PaPeissortexam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (PaPeissortexam)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:36
 */
public interface PaPeissortexamMapper extends BaseMapper<PaPeissortexam> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PaPeissortexam查询参数
     * @return 分页数据
     */
    IPage<PaPeissortexam> getPage(PageParam<PaPeissortexam> page, @Param("param") PaPeissortexam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PaPeissortexam getInfoById(@Param("id") String id);

}
