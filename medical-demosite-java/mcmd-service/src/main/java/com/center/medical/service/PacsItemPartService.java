package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsItemPart;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 项目部位表(PacsItemPart)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:29
 */
public interface PacsItemPartService extends IService<PacsItemPart> {

    /**
     * 分页查询[项目部位表]列表
     *
     * @param page  分页参数
     * @param param PacsItemPart查询参数
     * @return 分页数据
     */
    IPage<PacsItemPart> getList(PageParam<PacsItemPart> page, PacsItemPart param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsItemPart getInfoById(String id);

}

