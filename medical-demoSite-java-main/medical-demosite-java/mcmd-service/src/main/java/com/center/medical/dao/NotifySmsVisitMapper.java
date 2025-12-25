package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.NotifySmsVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 短信回访表(NotifySmsVisit)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:47
 */
public interface NotifySmsVisitMapper extends BaseMapper<NotifySmsVisit> {

    /**
     * 分页查询[短信回访表]列表
     *
     * @param page  分页参数
     * @param param NotifySmsVisit查询参数
     * @return 分页数据
     */
    IPage<NotifySmsVisit> getList(PageParam<NotifySmsVisit> page, @Param("param") NotifySmsVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    NotifySmsVisit getInfoById(@Param("id") String id);

}
