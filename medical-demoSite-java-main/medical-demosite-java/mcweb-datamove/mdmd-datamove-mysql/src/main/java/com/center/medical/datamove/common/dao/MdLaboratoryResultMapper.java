package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdLaboratoryResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS检验科接收数据(MdLaboratoryResult)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
public interface MdLaboratoryResultMapper extends BaseMapper<MdLaboratoryResult> {

    /**
     * 分页查询[KS检验科接收数据]列表
     *
     * @param page  分页参数
     * @param param MdLaboratoryResult查询参数
     * @return 分页数据
     */
    IPage<MdLaboratoryResult> getPage(PageParam<MdLaboratoryResult> page, @Param("param") MdLaboratoryResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdLaboratoryResult getInfoById(@Param("id") String id);

}
