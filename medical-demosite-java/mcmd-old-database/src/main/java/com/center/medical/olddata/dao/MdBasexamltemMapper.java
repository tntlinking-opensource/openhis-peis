package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdBasexamltem;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目表(MdBasexamltem)数据库访问层
 *
 * @author ay
 * @since 2024-07-13 13:49:09
 */
public interface MdBasexamltemMapper extends BaseMapper<MdBasexamltem> {

    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param MdBasexamltem查询参数
     * @return 分页数据
     */
    IPage<MdBasexamltem> getPage(PageParam<MdBasexamltem> page, @Param("param") MdBasexamltem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasexamltem getInfoById(@Param("id") String id);

}
