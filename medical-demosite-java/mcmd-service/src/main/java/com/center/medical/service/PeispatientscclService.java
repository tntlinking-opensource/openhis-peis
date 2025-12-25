package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientsccl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检号生成策略(Peispatientsccl)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:04
 */
public interface PeispatientscclService extends IService<Peispatientsccl> {

    /**
     * 分页查询[体检号生成策略]列表
     *
     * @param page  分页参数
     * @param param Peispatientsccl查询参数
     * @return 分页数据
     */
    IPage<Peispatientsccl> getList(PageParam<Peispatientsccl> page, Peispatientsccl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Peispatientsccl getInfoById(String id);

}

