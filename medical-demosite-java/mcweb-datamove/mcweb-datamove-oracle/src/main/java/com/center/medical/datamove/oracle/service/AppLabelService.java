package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppLabel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 微信小程序医生标签(AppLabel)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:41
 */
public interface AppLabelService extends IService<AppLabel> {

    /**
     * 分页查询[微信小程序医生标签]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppLabel> getPage(PageParam<AppLabel> page, AppLabel param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppLabel getInfoById(String id);

}

