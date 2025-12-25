package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.FxCompletion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(FxCompletion)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:47
 */
public interface FxCompletionService extends IService<FxCompletion> {

    /**
     * 分页查询[本次职业健康检查漏检人员及漏检项目人员一览表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FxCompletion> getPage(PageParam<FxCompletion> page, FxCompletion param);


}

