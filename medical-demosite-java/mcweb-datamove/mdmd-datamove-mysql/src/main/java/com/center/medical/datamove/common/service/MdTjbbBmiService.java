package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdTjbbBmi;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS体重指数体检报表(MdTjbbBmi)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:00
 */
public interface MdTjbbBmiService extends IService<MdTjbbBmi> {

    /**
     * 分页查询[KS体重指数体检报表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTjbbBmi> getPage(PageParam<MdTjbbBmi> page, MdTjbbBmi param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjbbBmi getInfoById(String id);

}

