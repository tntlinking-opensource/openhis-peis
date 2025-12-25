package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBkPatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者(MdBkPatient)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
public interface MdBkPatientMapper extends BaseMapper<MdBkPatient> {

    /**
     * 分页查询[体检者]列表
     *
     * @param page  分页参数
     * @param param MdBkPatient查询参数
     * @return 分页数据
     */
    IPage<MdBkPatient> getPage(PageParam<MdBkPatient> page, @Param("param") MdBkPatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBkPatient getInfoById(@Param("id") String id);

}
