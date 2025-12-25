package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PacsBasexamltem;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-基础检查项(PacsBasexamltem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:55
 */
public interface PacsBasexamltemsMapper extends BaseMapper<PacsBasexamltem> {

    /**
     * 分页查询[PACS-基础检查项]列表
     *
     * @param page  分页参数
     * @param param PacsBasexamltem查询参数
     * @return 分页数据
     */
    IPage<PacsBasexamltem> getList(PageParam<PacsBasexamltem> page, @Param("param") PacsBasexamltem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsBasexamltem getInfoById(@Param("id") String id);

}
