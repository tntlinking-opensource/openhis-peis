package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.LaboratoryResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS检验科接收数据(LaboratoryResult)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:50
 */
public interface LaboratoryResultService extends IService<LaboratoryResult> {

    /**
     * 分页查询[KS检验科接收数据]列表
     *
     * @param page  分页参数
     * @param param LaboratoryResult查询参数
     * @return 分页数据
     */
    IPage<LaboratoryResult> getList(PageParam<LaboratoryResult> page, LaboratoryResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    LaboratoryResult getInfoById(String id);

}

