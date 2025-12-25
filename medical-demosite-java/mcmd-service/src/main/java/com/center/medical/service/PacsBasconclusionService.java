package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsBasconclusion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * pacs总检结论词(PacsBasconclusion)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:15
 */
public interface PacsBasconclusionService extends IService<PacsBasconclusion> {

    /**
     * 分页查询[pacs总检结论词]列表
     *
     * @param page  分页参数
     * @param param PacsBasconclusion查询参数
     * @return 分页数据
     */
    IPage<PacsBasconclusion> getList(PageParam<PacsBasconclusion> page, PacsBasconclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsBasconclusion getInfoById(String id);

}

