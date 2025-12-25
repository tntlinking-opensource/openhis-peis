package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.LaboratoryResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS检验科接收数据(LaboratoryResult)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:32
 */
public interface LaboratoryResultMapper extends BaseMapper<LaboratoryResult> {

    /**
     * 分页查询[KS检验科接收数据]列表
     *
     * @param page  分页参数
     * @param param LaboratoryResult查询参数
     * @return 分页数据
     */
    IPage<LaboratoryResult> getPage(PageParam<LaboratoryResult> page, @Param("param") LaboratoryResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    LaboratoryResult getInfoById(@Param("id") String id);

}
