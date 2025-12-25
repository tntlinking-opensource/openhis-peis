package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSamplePerson;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * TJ团检报告人员样本表(MdSamplePerson)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:26
 */
public interface MdSamplePersonService extends IService<MdSamplePerson> {

    /**
     * 分页查询[TJ团检报告人员样本表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSamplePerson> getPage(PageParam<MdSamplePerson> page, MdSamplePerson param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSamplePerson getInfoById(String id);

}

