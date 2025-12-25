package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.NotifySmsVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 短信回访表(NotifySmsVisit)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:13
 */
public interface NotifySmsVisitMapper extends BaseMapper<NotifySmsVisit> {

    /**
     * 分页查询[短信回访表]列表
     *
     * @param page  分页参数
     * @param param NotifySmsVisit查询参数
     * @return 分页数据
     */
    IPage<NotifySmsVisit> getPage(PageParam<NotifySmsVisit> page, @Param("param") NotifySmsVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    NotifySmsVisit getInfoById(@Param("id") String id);

}
