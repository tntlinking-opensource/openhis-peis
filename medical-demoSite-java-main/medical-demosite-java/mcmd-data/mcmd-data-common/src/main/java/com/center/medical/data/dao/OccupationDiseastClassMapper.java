package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.OccupationDiseastClass;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病种分类(OccupationDiseastClass)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:16
 */
public interface OccupationDiseastClassMapper extends BaseMapper<OccupationDiseastClass> {

    /**
     * 分页查询[JC职业病种分类]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseastClass查询参数
     * @return 分页数据
     */
    IPage<OccupationDiseastClass> getList(PageParam<OccupationDiseastClass> page, @Param("param") OccupationDiseastClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OccupationDiseastClass getInfoById(@Param("id") String id);

}
