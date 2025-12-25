package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.WsLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 网站日志(WsLog)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
public interface WsLogMapper extends BaseMapper<WsLog> {

    /**
     * 分页查询[网站日志]列表
     *
     * @param page  分页参数
     * @param param WsLog查询参数
     * @return 分页数据
     */
    IPage<WsLog> getPage(PageParam<WsLog> page, @Param("param") WsLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WsLog getInfoById(@Param("id") String id);

}
