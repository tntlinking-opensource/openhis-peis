package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.FxDetection;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 本次体检异常结果检出统计(FxDetection)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:49
 */
public interface FxDetectionService extends IService<FxDetection> {

    /**
     * 分页查询[本次体检异常结果检出统计]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FxDetection> getPage(PageParam<FxDetection> page, FxDetection param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxDetection getInfoById(String id);

}

