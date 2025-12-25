package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Whysqzfw;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC危害因素取值范围(Whysqzfw)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:56
 */
public interface WhysqzfwService extends IService<Whysqzfw> {

    /**
     * 分页查询[JC危害因素取值范围]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Whysqzfw> getPage(PageParam<Whysqzfw> page, Whysqzfw param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Whysqzfw getInfoById(String id);

}

