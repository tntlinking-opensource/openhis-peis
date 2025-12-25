package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdCurrentQueueInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 当前排队绑定信息(MdCurrentQueueInfo)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
public interface MdCurrentQueueInfoService extends IService<MdCurrentQueueInfo> {

    /**
     * 分页查询[当前排队绑定信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCurrentQueueInfo> getPage(PageParam<MdCurrentQueueInfo> page, MdCurrentQueueInfo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCurrentQueueInfo getInfoById(String id);

}

