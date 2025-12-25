package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FxSummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 本次职业健康检查危害因素人员检查情况汇总一览表(FxSummary)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:26
 */
public interface FxSummaryMapper extends BaseMapper<FxSummary> {

    /**
     * 分页查询[本次职业健康检查危害因素人员检查情况汇总一览表]列表
     *
     * @param page  分页参数
     * @param param FxSummary查询参数
     * @return 分页数据
     */
    IPage<FxSummary> getList(PageParam<FxSummary> page, @Param("param") FxSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxSummary getInfoById(@Param("id") String id);

}
