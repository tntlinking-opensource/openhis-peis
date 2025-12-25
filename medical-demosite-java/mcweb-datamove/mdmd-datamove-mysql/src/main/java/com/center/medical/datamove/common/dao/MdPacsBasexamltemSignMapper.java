package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsBasexamltemSign;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-体征词(MdPacsBasexamltemSign)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
public interface MdPacsBasexamltemSignMapper extends BaseMapper<MdPacsBasexamltemSign> {

    /**
     * 分页查询[PACS-体征词]列表
     *
     * @param page  分页参数
     * @param param MdPacsBasexamltemSign查询参数
     * @return 分页数据
     */
    IPage<MdPacsBasexamltemSign> getPage(PageParam<MdPacsBasexamltemSign> page, @Param("param") MdPacsBasexamltemSign param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsBasexamltemSign getInfoById(@Param("id") String id);

}
