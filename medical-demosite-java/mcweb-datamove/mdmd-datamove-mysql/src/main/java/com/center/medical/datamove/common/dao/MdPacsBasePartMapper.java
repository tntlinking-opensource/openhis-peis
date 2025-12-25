package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsBasePart;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-部位(MdPacsBasePart)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
public interface MdPacsBasePartMapper extends BaseMapper<MdPacsBasePart> {

    /**
     * 分页查询[PACS-部位]列表
     *
     * @param page  分页参数
     * @param param MdPacsBasePart查询参数
     * @return 分页数据
     */
    IPage<MdPacsBasePart> getPage(PageParam<MdPacsBasePart> page, @Param("param") MdPacsBasePart param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsBasePart getInfoById(@Param("id") String id);

}
