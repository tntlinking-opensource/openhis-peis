package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.TotalDoctor;
import org.apache.ibatis.annotations.Param;

/**
 * 总检-医生 关联表(TotalDoctor)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-08 17:32:57
 */
public interface TotalDoctorMapper extends BaseMapper<TotalDoctor> {

    /**
     * 分页查询[总检-医生 关联表]列表
     *
     * @param page  分页参数
     * @param param TotalDoctor查询参数
     * @return 分页数据
     */
    IPage<TotalDoctor> getPage(PageParam<TotalDoctor> page, @Param("param") TotalDoctor param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TotalDoctor getInfoById(@Param("id") String id);

}
