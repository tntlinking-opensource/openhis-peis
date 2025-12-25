package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBusinessSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 合作第三方(MdBusinessSource)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
public interface MdBusinessSourceService extends IService<MdBusinessSource> {

    /**
     * 分页查询[合作第三方]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBusinessSource> getPage(PageParam<MdBusinessSource> page, MdBusinessSource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBusinessSource getInfoById(Object id);

}

