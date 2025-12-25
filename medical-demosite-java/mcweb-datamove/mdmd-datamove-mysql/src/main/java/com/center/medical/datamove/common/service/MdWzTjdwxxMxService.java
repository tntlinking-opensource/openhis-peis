package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdWzTjdwxxMx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——单位明细信息(MdWzTjdwxxMx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:25
 */
public interface MdWzTjdwxxMxService extends IService<MdWzTjdwxxMx> {

    /**
     * 分页查询[KS问诊——单位明细信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdWzTjdwxxMx> getPage(PageParam<MdWzTjdwxxMx> page, MdWzTjdwxxMx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzTjdwxxMx getInfoById(String id);

}

