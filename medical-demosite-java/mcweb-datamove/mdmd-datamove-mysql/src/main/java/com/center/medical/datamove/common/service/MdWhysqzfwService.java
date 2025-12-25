package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdWhysqzfw;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC危害因素取值范围(MdWhysqzfw)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:33
 */
public interface MdWhysqzfwService extends IService<MdWhysqzfw> {

    /**
     * 分页查询[JC危害因素取值范围]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdWhysqzfw> getPage(PageParam<MdWhysqzfw> page, MdWhysqzfw param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWhysqzfw getInfoById(String id);

}

