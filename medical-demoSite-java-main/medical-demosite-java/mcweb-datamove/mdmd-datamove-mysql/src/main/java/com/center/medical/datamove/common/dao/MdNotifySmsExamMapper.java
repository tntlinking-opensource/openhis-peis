package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdNotifySmsExam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 来检短信提醒表(MdNotifySmsExam)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
public interface MdNotifySmsExamMapper extends BaseMapper<MdNotifySmsExam> {

    /**
     * 分页查询[来检短信提醒表]列表
     *
     * @param page  分页参数
     * @param param MdNotifySmsExam查询参数
     * @return 分页数据
     */
    IPage<MdNotifySmsExam> getPage(PageParam<MdNotifySmsExam> page, @Param("param") MdNotifySmsExam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdNotifySmsExam getInfoById(@Param("id") String id);

}
