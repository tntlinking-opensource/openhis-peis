package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PeisState;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者上传状态(PeisState)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:02
 */
public interface PeisStateMapper extends BaseMapper<PeisState> {

    /**
     * 分页查询[体检者上传状态]列表
     *
     * @param page  分页参数
     * @param param PeisState查询参数
     * @return 分页数据
     */
    IPage<PeisState> getPage(PageParam<PeisState> page, @Param("param") PeisState param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisState getInfoById(@Param("id") String id);

}
