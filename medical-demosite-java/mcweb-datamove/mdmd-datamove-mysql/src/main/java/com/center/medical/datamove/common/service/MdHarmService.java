package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdHarm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC危害因素(MdHarm)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
public interface MdHarmService extends IService<MdHarm> {

    /**
     * 分页查询[JC危害因素]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdHarm> getPage(PageParam<MdHarm> page, MdHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdHarm getInfoById(String id);

}

