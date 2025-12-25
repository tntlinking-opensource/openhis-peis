package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.OperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志(OperateLog)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:43
 */
public interface OperateLogMapper extends BaseMapper<OperateLog> {

    /**
     * 分页查询[操作日志]列表
     *
     * @param page  分页参数
     * @param param OperateLog查询参数
     * @return 分页数据
     */
    IPage<OperateLog> getList(PageParam<OperateLog> page, @Param("param") OperateLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OperateLog getInfoById(@Param("id") String id);

}
