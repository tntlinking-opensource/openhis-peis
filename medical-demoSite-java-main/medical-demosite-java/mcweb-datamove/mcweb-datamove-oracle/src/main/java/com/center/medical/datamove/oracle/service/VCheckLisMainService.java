package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.VCheckLisMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (VCheckLisMain)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:49
 */
public interface VCheckLisMainService extends IService<VCheckLisMain> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<VCheckLisMain> getPage(PageParam<VCheckLisMain> page, VCheckLisMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键checkreqid
     * @return 详情信息
     */
    VCheckLisMain getInfoById(Object id);

}

