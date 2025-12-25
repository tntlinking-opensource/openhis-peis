package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF短信通知记录(SmsRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:00
 */
public interface SmsRecordMapper extends BaseMapper<SmsRecord> {

    /**
     * 分页查询[KF短信通知记录]列表
     *
     * @param page  分页参数
     * @param param SmsRecord查询参数
     * @return 分页数据
     */
    IPage<SmsRecord> getPage(PageParam<SmsRecord> page, @Param("param") SmsRecord param);


}
