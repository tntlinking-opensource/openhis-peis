package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdBasexamltemSign;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目体证词关联表(MdBasexamltemSign)数据库访问层
 *
 * @author ay
 * @since 2024-07-13 13:49:10
 */
public interface MdBasexamltemSignMapper extends BaseMapper<MdBasexamltemSign> {

    /**
     * 分页查询[JC检查项目体证词关联表]列表
     *
     * @param page  分页参数
     * @param param MdBasexamltemSign查询参数
     * @return 分页数据
     */
    IPage<MdBasexamltemSign> getPage(PageParam<MdBasexamltemSign> page, @Param("param") MdBasexamltemSign param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasexamltemSign getInfoById(@Param("id") String id);

}
