package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.LaboratoryResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS检验科接收数据(LaboratoryResult)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:33
 */
public interface LaboratoryResultService extends IService<LaboratoryResult> {

    /**
     * 分页查询[KS检验科接收数据]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<LaboratoryResult> getPage(PageParam<LaboratoryResult> page, LaboratoryResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    LaboratoryResult getInfoById(String id);

}

