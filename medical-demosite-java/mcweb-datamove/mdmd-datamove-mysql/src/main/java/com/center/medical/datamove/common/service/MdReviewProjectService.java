package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdReviewProject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * ZJ复查项目表(MdReviewProject)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:20
 */
public interface MdReviewProjectService extends IService<MdReviewProject> {

    /**
     * 分页查询[ZJ复查项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdReviewProject> getPage(PageParam<MdReviewProject> page, MdReviewProject param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReviewProject getInfoById(String id);

}

