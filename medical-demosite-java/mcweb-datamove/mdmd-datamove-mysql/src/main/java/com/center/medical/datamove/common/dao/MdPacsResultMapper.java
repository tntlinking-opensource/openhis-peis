package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-数据(MdPacsResult)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:35
 */
public interface MdPacsResultMapper extends BaseMapper<MdPacsResult> {

    /**
     * 分页查询[PACS-数据]列表
     *
     * @param page  分页参数
     * @param param MdPacsResult查询参数
     * @return 分页数据
     */
    IPage<MdPacsResult> getPage(PageParam<MdPacsResult> page, @Param("param") MdPacsResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsResult getInfoById(@Param("id") String id);

}
