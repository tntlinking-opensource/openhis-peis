package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdShReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 自助机-报告打印 操作记录(MdShReport)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:39
 */
public interface MdShReportMapper extends BaseMapper<MdShReport> {

    /**
     * 分页查询[自助机-报告打印 操作记录]列表
     *
     * @param page  分页参数
     * @param param MdShReport查询参数
     * @return 分页数据
     */
    IPage<MdShReport> getPage(PageParam<MdShReport> page, @Param("param") MdShReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdShReport getInfoById(@Param("id") String id);

}
