package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzTjdwxxMx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——单位明细信息(WzTjdwxxMx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
public interface WzTjdwxxMxService extends IService<WzTjdwxxMx> {

    /**
     * 分页查询[KS问诊——单位明细信息]列表
     *
     * @param page  分页参数
     * @param param WzTjdwxxMx查询参数
     * @return 分页数据
     */
    IPage<WzTjdwxxMx> getList(PageParam<WzTjdwxxMx> page, WzTjdwxxMx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzTjdwxxMx getInfoById(String id);

}

