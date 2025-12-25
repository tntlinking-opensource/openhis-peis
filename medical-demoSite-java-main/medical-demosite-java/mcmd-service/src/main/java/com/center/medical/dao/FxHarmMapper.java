package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FxHarm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业团检分析-危害因素(FxHarm)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:04
 */
public interface FxHarmMapper extends BaseMapper<FxHarm> {

    /**
     * 分页查询[职业团检分析-危害因素]列表
     *
     * @param page  分页参数
     * @param param FxHarm查询参数
     * @return 分页数据
     */
    IPage<FxHarm> getList(PageParam<FxHarm> page, @Param("param") FxHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxHarm getInfoById(@Param("id") String id);

}
