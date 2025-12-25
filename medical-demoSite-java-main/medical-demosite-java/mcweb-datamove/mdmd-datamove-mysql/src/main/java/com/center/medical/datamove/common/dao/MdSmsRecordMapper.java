package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 短信发送记录(MdSmsRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:43
 */
public interface MdSmsRecordMapper extends BaseMapper<MdSmsRecord> {

    /**
     * 分页查询[短信发送记录]列表
     *
     * @param page  分页参数
     * @param param MdSmsRecord查询参数
     * @return 分页数据
     */
    IPage<MdSmsRecord> getPage(PageParam<MdSmsRecord> page, @Param("param") MdSmsRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSmsRecord getInfoById(@Param("id") String id);

}
