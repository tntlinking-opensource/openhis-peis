package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdTotalVerdict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * ZJ总检结论词表(MdTotalVerdict)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:11
 */
public interface MdTotalVerdictService extends IService<MdTotalVerdict> {

    /**
     * 分页查询[ZJ总检结论词表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTotalVerdict> getPage(PageParam<MdTotalVerdict> page, MdTotalVerdict param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTotalVerdict getInfoById(String id);

}

