package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppInfoType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * APP咨询类型(AppInfoType)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:39
 */
public interface AppInfoTypeMapper extends BaseMapper<AppInfoType> {

    /**
     * 分页查询[APP咨询类型]列表
     *
     * @param page  分页参数
     * @param param AppInfoType查询参数
     * @return 分页数据
     */
    IPage<AppInfoType> getPage(PageParam<AppInfoType> page, @Param("param") AppInfoType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppInfoType getInfoById(@Param("id") String id);

}
