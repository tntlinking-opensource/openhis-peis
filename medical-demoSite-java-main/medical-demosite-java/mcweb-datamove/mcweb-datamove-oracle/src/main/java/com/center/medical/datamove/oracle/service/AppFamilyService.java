package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppFamily;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 家人档案(不含本人)(AppFamily)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:34
 */
public interface AppFamilyService extends IService<AppFamily> {

    /**
     * 分页查询[家人档案(不含本人)]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppFamily> getPage(PageParam<AppFamily> page, AppFamily param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppFamily getInfoById(String id);

}

