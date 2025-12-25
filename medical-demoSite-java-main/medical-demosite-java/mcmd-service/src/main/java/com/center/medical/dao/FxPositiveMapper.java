package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FxPositive;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 综合分析-阳性小结(FxPositive)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:18
 */
public interface FxPositiveMapper extends BaseMapper<FxPositive> {

    /**
     * 分页查询[综合分析-阳性小结]列表
     *
     * @param page  分页参数
     * @param param FxPositive查询参数
     * @return 分页数据
     */
    IPage<FxPositive> getList(PageParam<FxPositive> page, @Param("param") FxPositive param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxPositive getInfoById(@Param("id") String id);

}
