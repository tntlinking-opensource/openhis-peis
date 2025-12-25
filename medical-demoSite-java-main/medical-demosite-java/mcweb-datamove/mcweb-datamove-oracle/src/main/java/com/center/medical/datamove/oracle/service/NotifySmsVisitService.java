package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.NotifySmsVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 短信回访表(NotifySmsVisit)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:14
 */
public interface NotifySmsVisitService extends IService<NotifySmsVisit> {

    /**
     * 分页查询[短信回访表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<NotifySmsVisit> getPage(PageParam<NotifySmsVisit> page, NotifySmsVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    NotifySmsVisit getInfoById(String id);

}

