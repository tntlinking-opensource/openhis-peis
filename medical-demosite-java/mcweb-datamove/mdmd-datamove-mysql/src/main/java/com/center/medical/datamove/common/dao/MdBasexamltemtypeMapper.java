package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBasexamltemtype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目类型表(MdBasexamltemtype)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:12
 */
public interface MdBasexamltemtypeMapper extends BaseMapper<MdBasexamltemtype> {

    /**
     * 分页查询[JC检查项目类型表]列表
     *
     * @param page  分页参数
     * @param param MdBasexamltemtype查询参数
     * @return 分页数据
     */
    IPage<MdBasexamltemtype> getPage(PageParam<MdBasexamltemtype> page, @Param("param") MdBasexamltemtype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasexamltemtype getInfoById(@Param("id") String id);

}
