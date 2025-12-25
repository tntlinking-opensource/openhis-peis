package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsBasconclusion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * pacs总检结论词(MdPacsBasconclusion)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
public interface MdPacsBasconclusionMapper extends BaseMapper<MdPacsBasconclusion> {

    /**
     * 分页查询[pacs总检结论词]列表
     *
     * @param page  分页参数
     * @param param MdPacsBasconclusion查询参数
     * @return 分页数据
     */
    IPage<MdPacsBasconclusion> getPage(PageParam<MdPacsBasconclusion> page, @Param("param") MdPacsBasconclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsBasconclusion getInfoById(@Param("id") String id);

}
