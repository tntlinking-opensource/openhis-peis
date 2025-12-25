package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPricture;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS图片存储表(MdPricture)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
public interface MdPrictureService extends IService<MdPricture> {

    /**
     * 分页查询[KS图片存储表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPricture> getPage(PageParam<MdPricture> page, MdPricture param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPricture getInfoById(String id);

}

