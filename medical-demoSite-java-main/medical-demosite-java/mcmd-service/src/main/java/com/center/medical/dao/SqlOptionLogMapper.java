package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SqlOptionLog;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * SQL操作日志表(SqlOptionLog)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-06-21 14:33:20
 */
public interface SqlOptionLogMapper extends BaseMapper<SqlOptionLog> {

    /**
     * 分页查询[SQL操作日志表]列表
     *
     * @param page  分页参数
     * @param param SqlOptionLog查询参数
     * @return 分页数据
     */
    IPage<SqlOptionLog> getPage(PageParam<SqlOptionLog> page, @Param("param") SqlOptionLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SqlOptionLog getInfoById(@Param("id") Long id);

}
