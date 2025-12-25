package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmTeamremind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户预检跟踪表(CrmTeamremind)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:02
 */
public interface CrmTeamremindMapper extends BaseMapper<CrmTeamremind> {

    /**
     * 分页查询[客户预检跟踪表]列表
     *
     * @param page  分页参数
     * @param param CrmTeamremind查询参数
     * @return 分页数据
     */
    IPage<CrmTeamremind> getPage(PageParam<CrmTeamremind> page, @Param("param") CrmTeamremind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmTeamremind getInfoById(@Param("id") String id);

}
