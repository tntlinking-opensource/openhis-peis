package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.BusinessSource;
import com.center.medical.system.bean.param.BusinessSourceParam;

/**
 * 合作第三方(BusinessSource)表服务接口
 *
 * @author 路飞船长
 * @since 2023-03-24 10:10:24
 */
public interface BusinessSourceService extends IService<BusinessSource> {

    /**
     * 分页查询[合作第三方]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BusinessSource> getPage(PageParam<BusinessSource> page, BusinessSourceParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BusinessSource getInfoById(Long id);

}

