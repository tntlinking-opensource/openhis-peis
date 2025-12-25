package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppFamily;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 家人档案(不含本人)(AppFamily)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:33
 */
public interface AppFamilyMapper extends BaseMapper<AppFamily> {

    /**
     * 分页查询[家人档案(不含本人)]列表
     *
     * @param page  分页参数
     * @param param AppFamily查询参数
     * @return 分页数据
     */
    IPage<AppFamily> getPage(PageParam<AppFamily> page, @Param("param") AppFamily param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppFamily getInfoById(@Param("id") String id);

}
