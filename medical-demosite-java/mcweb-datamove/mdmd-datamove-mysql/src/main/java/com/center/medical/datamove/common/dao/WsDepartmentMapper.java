package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.WsDepartment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 网站部门(WsDepartment)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
public interface WsDepartmentMapper extends BaseMapper<WsDepartment> {

    /**
     * 分页查询[网站部门]列表
     *
     * @param page  分页参数
     * @param param WsDepartment查询参数
     * @return 分页数据
     */
    IPage<WsDepartment> getPage(PageParam<WsDepartment> page, @Param("param") WsDepartment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WsDepartment getInfoById(@Param("id") String id);

}
