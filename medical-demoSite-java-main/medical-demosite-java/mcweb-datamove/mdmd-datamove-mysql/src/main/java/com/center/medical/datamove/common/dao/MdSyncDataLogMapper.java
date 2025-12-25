package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSyncDataLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 同步数据操作记录(MdSyncDataLog)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:50
 */
public interface MdSyncDataLogMapper extends BaseMapper<MdSyncDataLog> {

    /**
     * 分页查询[同步数据操作记录]列表
     *
     * @param page  分页参数
     * @param param MdSyncDataLog查询参数
     * @return 分页数据
     */
    IPage<MdSyncDataLog> getPage(PageParam<MdSyncDataLog> page, @Param("param") MdSyncDataLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键logId
     * @return 详情信息
     */
    MdSyncDataLog getInfoById(@Param("id") Long id);

}
