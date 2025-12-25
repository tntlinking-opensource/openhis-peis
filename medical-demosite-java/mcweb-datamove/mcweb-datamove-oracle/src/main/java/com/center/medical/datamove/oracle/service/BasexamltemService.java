package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Basexamltem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC检查项目表(Basexamltem)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:43
 */
public interface BasexamltemService extends IService<Basexamltem> {

    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Basexamltem> getPage(PageParam<Basexamltem> page, Basexamltem param);


}

