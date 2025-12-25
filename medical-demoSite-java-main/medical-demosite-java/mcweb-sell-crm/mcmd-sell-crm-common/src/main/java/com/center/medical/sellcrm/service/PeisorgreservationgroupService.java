package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;

/**
 * 体检者任务分组(Peisorgreservationgroup)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:02
 */
public interface PeisorgreservationgroupService extends IService<Peisorgreservationgroup> {

    /**
     * 分页查询[体检者任务分组]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peisorgreservationgroup> getPage(PageParam<Peisorgreservationgroup> page, Peisorgreservationgroup param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservationgroup getInfoById(String id);

}

