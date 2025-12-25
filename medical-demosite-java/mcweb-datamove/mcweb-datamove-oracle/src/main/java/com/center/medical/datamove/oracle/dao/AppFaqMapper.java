package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppFaq;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * APP常见问题(AppFaq)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:34
 */
public interface AppFaqMapper extends BaseMapper<AppFaq> {

    /**
     * 分页查询[APP常见问题]列表
     *
     * @param page  分页参数
     * @param param AppFaq查询参数
     * @return 分页数据
     */
    IPage<AppFaq> getPage(PageParam<AppFaq> page, @Param("param") AppFaq param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppFaq getInfoById(@Param("id") String id);

}
