package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBaseGuideMealitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 基础收费项目(MdBaseGuideMealitem)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
public interface MdBaseGuideMealitemMapper extends BaseMapper<MdBaseGuideMealitem> {

    /**
     * 分页查询[基础收费项目]列表
     *
     * @param page  分页参数
     * @param param MdBaseGuideMealitem查询参数
     * @return 分页数据
     */
    IPage<MdBaseGuideMealitem> getPage(PageParam<MdBaseGuideMealitem> page, @Param("param") MdBaseGuideMealitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseGuideMealitem getInfoById(@Param("id") String id);

}
