package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdOccupationDiseastClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病种分类(MdOccupationDiseastClass)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
public interface MdOccupationDiseastClassMapper extends BaseMapper<MdOccupationDiseastClass> {

    /**
     * 分页查询[JC职业病种分类]列表
     *
     * @param page  分页参数
     * @param param MdOccupationDiseastClass查询参数
     * @return 分页数据
     */
    IPage<MdOccupationDiseastClass> getPage(PageParam<MdOccupationDiseastClass> page, @Param("param") MdOccupationDiseastClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOccupationDiseastClass getInfoById(@Param("id") String id);

}
