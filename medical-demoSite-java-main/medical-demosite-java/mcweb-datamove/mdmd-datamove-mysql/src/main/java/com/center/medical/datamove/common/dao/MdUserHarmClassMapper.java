package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUserHarmClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 总检医生-危害因素分类关联表 (MdUserHarmClass)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:17
 */
public interface MdUserHarmClassMapper extends BaseMapper<MdUserHarmClass> {

    /**
     * 分页查询[总检医生-危害因素分类关联表 ]列表
     *
     * @param page  分页参数
     * @param param MdUserHarmClass查询参数
     * @return 分页数据
     */
    IPage<MdUserHarmClass> getPage(PageParam<MdUserHarmClass> page, @Param("param") MdUserHarmClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUserHarmClass getInfoById(@Param("id") String id);

}
