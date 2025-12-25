package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSatisfaction;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KF满意度(MdSatisfaction)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:27
 */
public interface MdSatisfactionService extends IService<MdSatisfaction> {

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSatisfaction> getPage(PageParam<MdSatisfaction> page, MdSatisfaction param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSatisfaction getInfoById(String id);

}

