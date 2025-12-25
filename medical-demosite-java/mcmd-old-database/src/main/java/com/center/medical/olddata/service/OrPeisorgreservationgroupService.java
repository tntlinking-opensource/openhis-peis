package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeisorgreservationgroup;

import java.util.List;

/**
 * QT体检团体任务分组表(Peisorgreservationgroup)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:54:58
 */
public interface OrPeisorgreservationgroupService extends IService<OrPeisorgreservationgroup> {

    /**
     * 分页查询[QT体检团体任务分组表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrPeisorgreservationgroup> getPage(PageParam<OrPeisorgreservationgroup> page, OrPeisorgreservationgroup param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeisorgreservationgroup getInfoById(String id);

    /**
     * 根据任务id获取分组
     *
     * @param taskId
     * @return
     */
    List<OrPeisorgreservationgroup> getGroupList(String taskId);
}

