package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Sshy;

/**
 * 创建团体客户要选择的所属行业在这里维护(Sshy)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-19 14:11:08
 */
public interface SshyService extends IService<Sshy> {

    /**
     * 分页查询[创建团体客户要选择的所属行业在这里维护]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Sshy> getPage(PageParam<Sshy> page, Sshy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sshy getInfoById(String id);

}

