package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.ShReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 自助机-报告打印 操作记录(ShReport)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:25
 */
public interface ShReportMapper extends BaseMapper<ShReport> {

    /**
     * 分页查询[自助机-报告打印 操作记录]列表
     *
     * @param page  分页参数
     * @param param ShReport查询参数
     * @return 分页数据
     */
    IPage<ShReport> getList(PageParam<ShReport> page, @Param("param") ShReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ShReport getInfoById(@Param("id") String id);

}
