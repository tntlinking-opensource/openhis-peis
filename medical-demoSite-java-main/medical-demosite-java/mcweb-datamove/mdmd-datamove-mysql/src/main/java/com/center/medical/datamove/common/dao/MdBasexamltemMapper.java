package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBasexamltem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目表(MdBasexamltem)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:11
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
