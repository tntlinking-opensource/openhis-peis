package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.TjbbBmi;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS体重指数体检报表(TjbbBmi)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:23
 */
public interface TjbbBmiService extends IService<TjbbBmi> {

    /**
     * 分页查询[KS体重指数体检报表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TjbbBmi> getPage(PageParam<TjbbBmi> page, TjbbBmi param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TjbbBmi getInfoById(String id);

}

