package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ZyFhclGr;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC个人防护用品(ZyFhclGr)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:54
 */
public interface ZyFhclGrService extends IService<ZyFhclGr> {

    /**
     * 分页查询[JC个人防护用品]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ZyFhclGr> getPage(PageParam<ZyFhclGr> page, ZyFhclGr param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyFhclGr getInfoById(String id);

}

