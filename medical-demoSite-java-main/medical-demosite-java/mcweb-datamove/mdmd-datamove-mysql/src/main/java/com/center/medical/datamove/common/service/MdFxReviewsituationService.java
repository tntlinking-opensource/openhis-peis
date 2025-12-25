package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFxReviewsituation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 本次职业健康检查复查人员复查情况一览表(MdFxReviewsituation)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
public interface MdFxReviewsituationService extends IService<MdFxReviewsituation> {

    /**
     * 分页查询[本次职业健康检查复查人员复查情况一览表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFxReviewsituation> getPage(PageParam<MdFxReviewsituation> page, MdFxReviewsituation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxReviewsituation getInfoById(String id);

}

