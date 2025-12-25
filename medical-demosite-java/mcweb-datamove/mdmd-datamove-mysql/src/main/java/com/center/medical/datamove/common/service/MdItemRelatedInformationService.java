package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdItemRelatedInformation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 第三方接口关联记录(MdItemRelatedInformation)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:23
 */
public interface MdItemRelatedInformationService extends IService<MdItemRelatedInformation> {

    /**
     * 分页查询[第三方接口关联记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdItemRelatedInformation> getPage(PageParam<MdItemRelatedInformation> page, MdItemRelatedInformation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdItemRelatedInformation getInfoById(String id);

}

