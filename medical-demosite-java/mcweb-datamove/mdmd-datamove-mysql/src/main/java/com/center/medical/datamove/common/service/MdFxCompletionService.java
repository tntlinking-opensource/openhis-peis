package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFxCompletion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(MdFxCompletion)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
public interface MdFxCompletionService extends IService<MdFxCompletion> {

    /**
     * 分页查询[本次职业健康检查漏检人员及漏检项目人员一览表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFxCompletion> getPage(PageParam<MdFxCompletion> page, MdFxCompletion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxCompletion getInfoById(String id);

}

