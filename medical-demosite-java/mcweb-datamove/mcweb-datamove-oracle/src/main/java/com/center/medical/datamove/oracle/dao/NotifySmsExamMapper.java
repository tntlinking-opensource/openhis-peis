package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.NotifySmsExam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 来检短信提醒表(NotifySmsExam)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:12
 */
public interface NotifySmsExamMapper extends BaseMapper<NotifySmsExam> {

    /**
     * 分页查询[来检短信提醒表]列表
     *
     * @param page  分页参数
     * @param param NotifySmsExam查询参数
     * @return 分页数据
     */
    IPage<NotifySmsExam> getPage(PageParam<NotifySmsExam> page, @Param("param") NotifySmsExam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    NotifySmsExam getInfoById(@Param("id") String id);

}
