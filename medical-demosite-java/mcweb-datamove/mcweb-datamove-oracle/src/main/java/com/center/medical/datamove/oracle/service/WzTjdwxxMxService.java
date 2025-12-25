package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.WzTjdwxxMx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——单位明细信息(WzTjdwxxMx)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:52
 */
public interface WzTjdwxxMxService extends IService<WzTjdwxxMx> {

    /**
     * 分页查询[KS问诊——单位明细信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WzTjdwxxMx> getPage(PageParam<WzTjdwxxMx> page, WzTjdwxxMx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzTjdwxxMx getInfoById(String id);

}

