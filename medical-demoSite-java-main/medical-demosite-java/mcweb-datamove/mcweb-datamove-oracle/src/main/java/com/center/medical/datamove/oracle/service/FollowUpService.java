package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.FollowUp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC随访目的维护(FollowUp)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:46
 */
public interface FollowUpService extends IService<FollowUp> {

    /**
     * 分页查询[JC随访目的维护]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FollowUp> getPage(PageParam<FollowUp> page, FollowUp param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FollowUp getInfoById(String id);

}

