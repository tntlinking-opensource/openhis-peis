package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.VisitWrite;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KF回访记录表(VisitWrite)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:54
 */
public interface VisitWriteService extends IService<VisitWrite> {

    /**
     * 分页查询[KF回访记录表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<VisitWrite> getPage(PageParam<VisitWrite> page, VisitWrite param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    VisitWrite getInfoById(String id);

}

