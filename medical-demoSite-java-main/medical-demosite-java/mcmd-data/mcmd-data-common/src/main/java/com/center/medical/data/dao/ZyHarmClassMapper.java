package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZyHarmClass;
import org.apache.ibatis.annotations.Param;

/**
 * 职业危害因素分类(ZyHarmClass)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:00
 */
public interface ZyHarmClassMapper extends BaseMapper<ZyHarmClass> {

    /**
     * 分页查询[职业危害因素分类]列表
     *
     * @param page  分页参数
     * @param param ZyHarmClass查询参数
     * @return 分页数据
     */
    IPage<ZyHarmClass> getList(PageParam<ZyHarmClass> page, @Param("param") ZyHarmClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ZyHarmClass getInfoById(@Param("id") String id);

}
