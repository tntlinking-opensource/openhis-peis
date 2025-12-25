package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdLeadertarget;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 领导目标表(MdLeadertarget)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:25
 */
public interface MdLeadertargetService extends IService<MdLeadertarget> {

    /**
     * 分页查询[领导目标表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdLeadertarget> getPage(PageParam<MdLeadertarget> page, MdLeadertarget param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdLeadertarget getInfoById(String id);

}

