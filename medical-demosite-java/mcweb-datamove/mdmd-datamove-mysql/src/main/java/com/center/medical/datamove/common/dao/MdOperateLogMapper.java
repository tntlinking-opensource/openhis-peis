package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdOperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志(MdOperateLog)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:29
 */
public interface MdOperateLogMapper extends BaseMapper<MdOperateLog> {

    /**
     * 分页查询[操作日志]列表
     *
     * @param page  分页参数
     * @param param MdOperateLog查询参数
     * @return 分页数据
     */
    IPage<MdOperateLog> getPage(PageParam<MdOperateLog> page, @Param("param") MdOperateLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOperateLog getInfoById(@Param("id") String id);

}
