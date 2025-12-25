package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.DmyhResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS动脉硬化结果(DmyhResult)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:20
 */
public interface DmyhResultService extends IService<DmyhResult> {

    /**
     * 分页查询[KS动脉硬化结果]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DmyhResult> getPage(PageParam<DmyhResult> page, DmyhResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DmyhResult getInfoById(String id);

}

