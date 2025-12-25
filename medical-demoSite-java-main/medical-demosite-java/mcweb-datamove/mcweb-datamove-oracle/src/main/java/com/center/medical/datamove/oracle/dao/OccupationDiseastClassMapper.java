package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.OccupationDiseastClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病种分类(OccupationDiseastClass)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:18
 */
public interface OccupationDiseastClassMapper extends BaseMapper<OccupationDiseastClass> {

    /**
     * 分页查询[JC职业病种分类]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseastClass查询参数
     * @return 分页数据
     */
    IPage<OccupationDiseastClass> getPage(PageParam<OccupationDiseastClass> page, @Param("param") OccupationDiseastClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OccupationDiseastClass getInfoById(@Param("id") String id);

}
