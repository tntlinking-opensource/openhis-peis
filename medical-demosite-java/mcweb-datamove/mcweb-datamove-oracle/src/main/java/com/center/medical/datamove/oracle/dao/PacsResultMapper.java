package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PacsResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * Pacs数据(PacsResult)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:51
 */
public interface PacsResultMapper extends BaseMapper<PacsResult> {

    /**
     * 分页查询[Pacs数据]列表
     *
     * @param page  分页参数
     * @param param PacsResult查询参数
     * @return 分页数据
     */
    IPage<PacsResult> getPage(PageParam<PacsResult> page, @Param("param") PacsResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PacsResult getInfoById(@Param("id") String id);

}
