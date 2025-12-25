package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 日志(QxLog)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:46
 */
public interface QxLogMapper extends BaseMapper<QxLog> {

    /**
     * 分页查询[日志]列表
     *
     * @param page  分页参数
     * @param param QxLog查询参数
     * @return 分页数据
     */
    IPage<QxLog> getPage(PageParam<QxLog> page, @Param("param") QxLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    QxLog getInfoById(@Param("id") String id);

}
