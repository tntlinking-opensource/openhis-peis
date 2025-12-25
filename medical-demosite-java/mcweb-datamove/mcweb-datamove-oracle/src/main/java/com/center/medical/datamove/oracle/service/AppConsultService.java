package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppConsult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 微信小程序医生咨询(AppConsult)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:24
 */
public interface AppConsultService extends IService<AppConsult> {

    /**
     * 分页查询[微信小程序医生咨询]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppConsult> getPage(PageParam<AppConsult> page, AppConsult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppConsult getInfoById(String id);

}

