package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFxReviewInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 职业健康检查职业病危害效应相关指标异常需要复查人员(MdFxReviewInfo)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
public interface MdFxReviewInfoService extends IService<MdFxReviewInfo> {

    /**
     * 分页查询[职业健康检查职业病危害效应相关指标异常需要复查人员]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFxReviewInfo> getPage(PageParam<MdFxReviewInfo> page, MdFxReviewInfo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxReviewInfo getInfoById(String id);

}

