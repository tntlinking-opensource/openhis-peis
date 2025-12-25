package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.SmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 短信发送记录(SmsRecord)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:11
 */
public interface SmsRecordMapper extends BaseMapper<SmsRecord> {

    /**
     * 分页查询[短信发送记录]列表
     *
     * @param page  分页参数
     * @param param SmsRecord查询参数
     * @return 分页数据
     */
    IPage<SmsRecord> getList(PageParam<SmsRecord> page, @Param("param") SmsRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SmsRecord getInfoById(@Param("id") String id);

}
