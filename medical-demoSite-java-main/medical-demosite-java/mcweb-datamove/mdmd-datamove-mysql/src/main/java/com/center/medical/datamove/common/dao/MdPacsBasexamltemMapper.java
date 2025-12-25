package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsBasexamltem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-基础检查项(MdPacsBasexamltem)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
public interface MdPacsBasexamltemMapper extends BaseMapper<MdPacsBasexamltem> {

    /**
     * 分页查询[PACS-基础检查项]列表
     *
     * @param page  分页参数
     * @param param MdPacsBasexamltem查询参数
     * @return 分页数据
     */
    IPage<MdPacsBasexamltem> getPage(PageParam<MdPacsBasexamltem> page, @Param("param") MdPacsBasexamltem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsBasexamltem getInfoById(@Param("id") String id);

}
