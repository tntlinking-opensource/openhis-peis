package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PacsBasexamltem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (PacsBasexamltem)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:41
 */
public interface PacsBasexamltemMapper extends BaseMapper<PacsBasexamltem> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsBasexamltem查询参数
     * @return 分页数据
     */
    IPage<PacsBasexamltem> getPage(PageParam<PacsBasexamltem> page, @Param("param") PacsBasexamltem param);


}
