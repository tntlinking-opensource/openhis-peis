package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFailTotalVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KF迟捡、阳性、不合格样本回访(MdFailTotalVisit)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
public interface MdFailTotalVisitService extends IService<MdFailTotalVisit> {

    /**
     * 分页查询[KF迟捡、阳性、不合格样本回访]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFailTotalVisit> getPage(PageParam<MdFailTotalVisit> page, MdFailTotalVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFailTotalVisit getInfoById(String id);

}

