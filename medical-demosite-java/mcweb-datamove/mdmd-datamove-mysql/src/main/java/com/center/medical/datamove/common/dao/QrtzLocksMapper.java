package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.QrtzLocks;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 存储的悲观锁信息表(QrtzLocks)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
public interface QrtzLocksMapper extends BaseMapper<QrtzLocks> {

    /**
     * 分页查询[存储的悲观锁信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzLocks查询参数
     * @return 分页数据
     */
    IPage<QrtzLocks> getPage(PageParam<QrtzLocks> page, @Param("param") QrtzLocks param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzLocks getInfoById(@Param("id") String id);

}
