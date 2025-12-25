package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.DwHarm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC单位危害因素(DwHarm)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:32
 */
public interface DwHarmService extends IService<DwHarm> {

    /**
     * 分页查询[JC单位危害因素]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DwHarm> getPage(PageParam<DwHarm> page, DwHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DwHarm getInfoById(String id);

}

