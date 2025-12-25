package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsPdf;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS PDF  海康医院使用(MdPacsPdf)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
public interface MdPacsPdfMapper extends BaseMapper<MdPacsPdf> {

    /**
     * 分页查询[PACS PDF  海康医院使用]列表
     *
     * @param page  分页参数
     * @param param MdPacsPdf查询参数
     * @return 分页数据
     */
    IPage<MdPacsPdf> getPage(PageParam<MdPacsPdf> page, @Param("param") MdPacsPdf param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsPdf getInfoById(@Param("id") String id);

}
