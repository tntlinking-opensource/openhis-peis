package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.QrtzJobDetails;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 任务详细信息表(QrtzJobDetails)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
public interface QrtzJobDetailsMapper extends BaseMapper<QrtzJobDetails> {

    /**
     * 分页查询[任务详细信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzJobDetails查询参数
     * @return 分页数据
     */
    IPage<QrtzJobDetails> getPage(PageParam<QrtzJobDetails> page, @Param("param") QrtzJobDetails param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzJobDetails getInfoById(@Param("id") String id);

}
