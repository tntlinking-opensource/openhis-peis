package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBasconclusion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 总检结论词(MdBasconclusion)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
public interface MdBasconclusionService extends IService<MdBasconclusion> {

    /**
     * 分页查询[总检结论词]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBasconclusion> getPage(PageParam<MdBasconclusion> page, MdBasconclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasconclusion getInfoById(String id);

}

