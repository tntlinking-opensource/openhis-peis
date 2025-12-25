package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Teamremind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 客户预检跟踪表(Teamremind)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:14
 */
public interface TeamremindService extends IService<Teamremind> {

    /**
     * 分页查询[客户预检跟踪表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Teamremind> getPage(PageParam<Teamremind> page, Teamremind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Teamremind getInfoById(String id);

}

