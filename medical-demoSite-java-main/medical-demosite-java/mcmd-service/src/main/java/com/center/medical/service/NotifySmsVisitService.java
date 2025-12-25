package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.NotifySmsVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 短信回访表(NotifySmsVisit)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:47
 */
public interface NotifySmsVisitService extends IService<NotifySmsVisit> {

    /**
     * 分页查询[短信回访表]列表
     *
     * @param page  分页参数
     * @param param NotifySmsVisit查询参数
     * @return 分页数据
     */
    IPage<NotifySmsVisit> getList(PageParam<NotifySmsVisit> page, NotifySmsVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    NotifySmsVisit getInfoById(String id);

}

