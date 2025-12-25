package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FollowUp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC随访目的维护(FollowUp)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:25
 */
public interface FollowUpService extends IService<FollowUp> {

    /**
     * 分页查询[JC随访目的维护]列表
     *
     * @param page  分页参数
     * @param param FollowUp查询参数
     * @return 分页数据
     */
    IPage<FollowUp> getList(PageParam<FollowUp> page, FollowUp param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FollowUp getInfoById(String id);

}

