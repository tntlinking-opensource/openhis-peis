package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdVisitWrite;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KF回访记录表(MdVisitWrite)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:31
 */
public interface MdVisitWriteService extends IService<MdVisitWrite> {

    /**
     * 分页查询[KF回访记录表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdVisitWrite> getPage(PageParam<MdVisitWrite> page, MdVisitWrite param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdVisitWrite getInfoById(String id);

}

