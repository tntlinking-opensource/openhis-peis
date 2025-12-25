package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZySummary;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病检查结论(ZySummary)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:35
 */
public interface ZySummaryMapper extends BaseMapper<ZySummary> {

    /**
     * 分页查询[JC职业病检查结论]列表
     *
     * @param page  分页参数
     * @param param ZySummary查询参数
     * @return 分页数据
     */
    IPage<ZySummary> getList(PageParam<ZySummary> page, @Param("param") ZySummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ZySummary getInfoById(@Param("id") String id);

    /**
     * 根据主键id获取记录详情,没被删除的
     * @param id
     * @return
     */
    ZySummary getZySummaryById(String id);


}
