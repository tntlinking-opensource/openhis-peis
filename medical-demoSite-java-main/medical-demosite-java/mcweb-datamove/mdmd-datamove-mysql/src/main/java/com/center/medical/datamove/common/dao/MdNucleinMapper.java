package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdNuclein;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 核酸检测报告上传记录(MdNuclein)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
public interface MdNucleinMapper extends BaseMapper<MdNuclein> {

    /**
     * 分页查询[核酸检测报告上传记录]列表
     *
     * @param page  分页参数
     * @param param MdNuclein查询参数
     * @return 分页数据
     */
    IPage<MdNuclein> getPage(PageParam<MdNuclein> page, @Param("param") MdNuclein param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdNuclein getInfoById(@Param("id") String id);

}
