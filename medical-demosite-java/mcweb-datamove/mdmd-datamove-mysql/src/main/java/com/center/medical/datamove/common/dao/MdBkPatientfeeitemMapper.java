package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBkPatientfeeitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者收费项目(MdBkPatientfeeitem)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
public interface MdBkPatientfeeitemMapper extends BaseMapper<MdBkPatientfeeitem> {

    /**
     * 分页查询[体检者收费项目]列表
     *
     * @param page  分页参数
     * @param param MdBkPatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<MdBkPatientfeeitem> getPage(PageParam<MdBkPatientfeeitem> page, @Param("param") MdBkPatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBkPatientfeeitem getInfoById(@Param("id") String id);

}
