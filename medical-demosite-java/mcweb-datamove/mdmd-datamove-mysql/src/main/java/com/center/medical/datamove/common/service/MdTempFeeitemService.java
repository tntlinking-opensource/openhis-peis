package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdTempFeeitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 科室临时加项表(MdTempFeeitem)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:55
 */
public interface MdTempFeeitemService extends IService<MdTempFeeitem> {

    /**
     * 分页查询[科室临时加项表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTempFeeitem> getPage(PageParam<MdTempFeeitem> page, MdTempFeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTempFeeitem getInfoById(String id);

}

