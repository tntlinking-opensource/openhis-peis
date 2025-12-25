package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.LisResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * LIS接收结果(LisResult)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:39
 */
public interface LisResultService extends IService<LisResult> {

    /**
     * 分页查询[LIS接收结果]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<LisResult> getPage(PageParam<LisResult> page, LisResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    LisResult getInfoById(String id);

}

