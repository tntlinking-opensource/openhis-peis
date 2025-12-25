package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FxPersonnelview;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 综合分析-人员一览表(FxPersonnelview)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:19
 */
public interface FxPersonnelviewMapper extends BaseMapper<FxPersonnelview> {

    /**
     * 分页查询[综合分析-人员一览表]列表
     *
     * @param page  分页参数
     * @param param FxPersonnelview查询参数
     * @return 分页数据
     */
    IPage<FxPersonnelview> getList(PageParam<FxPersonnelview> page, @Param("param") FxPersonnelview param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxPersonnelview getInfoById(@Param("id") String id);

}
