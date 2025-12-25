package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.VisitWrite;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KF回访记录表(VisitWrite)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:54
 */
public interface VisitWriteService extends IService<VisitWrite> {

    /**
     * 分页查询[KF回访记录表]列表
     *
     * @param page  分页参数
     * @param param VisitWrite查询参数
     * @return 分页数据
     */
    IPage<VisitWrite> getList(PageParam<VisitWrite> page, VisitWrite param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    VisitWrite getInfoById(String id);

}

