package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsPdf;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS PDF  海康医院使用(PacsPdf)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:36
 */
public interface PacsPdfService extends IService<PacsPdf> {

    /**
     * 分页查询[PACS PDF  海康医院使用]列表
     *
     * @param page  分页参数
     * @param param PacsPdf查询参数
     * @return 分页数据
     */
    IPage<PacsPdf> getList(PageParam<PacsPdf> page, PacsPdf param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsPdf getInfoById(String id);

}

