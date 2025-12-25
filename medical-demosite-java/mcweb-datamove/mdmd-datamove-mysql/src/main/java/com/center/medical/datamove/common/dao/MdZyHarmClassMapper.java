package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdZyHarmClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业危害因素分类(MdZyHarmClass)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
public interface MdZyHarmClassMapper extends BaseMapper<MdZyHarmClass> {

    /**
     * 分页查询[职业危害因素分类]列表
     *
     * @param page  分页参数
     * @param param MdZyHarmClass查询参数
     * @return 分页数据
     */
    IPage<MdZyHarmClass> getPage(PageParam<MdZyHarmClass> page, @Param("param") MdZyHarmClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZyHarmClass getInfoById(@Param("id") String id);

}
