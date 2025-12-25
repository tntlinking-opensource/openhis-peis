package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.app.bean.model.FamilyList;
import com.center.medical.app.common.util.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 家人列表(FamilyList)数据库访问层
 *
 * @author ay
 * @since 2024-03-13 14:31:01
 */
public interface FamilyListMapper extends BaseMapper<FamilyList> {

    /**
     * 分页查询[家人列表]列表
     *
     * @param page  分页参数
     * @param param FamilyList查询参数
     * @return 分页数据
     */
    IPage<FamilyList> getPage(PageParam<FamilyList> page, @Param("param") FamilyList param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FamilyList getInfoById(@Param("id") String id);

}
