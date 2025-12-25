package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BatchSmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 批量发送短信记录(BatchSmsRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:47
 */
public interface BatchSmsRecordMapper extends BaseMapper<BatchSmsRecord> {

    /**
     * 分页查询[批量发送短信记录]列表
     *
     * @param page  分页参数
     * @param param BatchSmsRecord查询参数
     * @return 分页数据
     */
    IPage<BatchSmsRecord> getPage(PageParam<BatchSmsRecord> page, @Param("param") BatchSmsRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BatchSmsRecord getInfoById(@Param("id") String id);

}
