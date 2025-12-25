package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBatchSmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 批量发送短信记录(MdBatchSmsRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:12
 */
public interface MdBatchSmsRecordMapper extends BaseMapper<MdBatchSmsRecord> {

    /**
     * 分页查询[批量发送短信记录]列表
     *
     * @param page  分页参数
     * @param param MdBatchSmsRecord查询参数
     * @return 分页数据
     */
    IPage<MdBatchSmsRecord> getPage(PageParam<MdBatchSmsRecord> page, @Param("param") MdBatchSmsRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBatchSmsRecord getInfoById(@Param("id") String id);

}
