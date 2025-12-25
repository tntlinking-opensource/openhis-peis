package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdWzJwb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——既往病(MdWzJwb)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:23
 */
public interface MdWzJwbService extends IService<MdWzJwb> {

    /**
     * 分页查询[KS问诊——既往病]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdWzJwb> getPage(PageParam<MdWzJwb> page, MdWzJwb param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzJwb getInfoById(String id);

}

