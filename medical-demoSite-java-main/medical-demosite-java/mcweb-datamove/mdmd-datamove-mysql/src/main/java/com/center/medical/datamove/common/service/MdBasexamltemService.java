package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBasexamltem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC检查项目表(MdBasexamltem)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:12
 */
public interface MdBasexamltemService extends IService<MdBasexamltem> {

    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBasexamltem> getPage(PageParam<MdBasexamltem> page, MdBasexamltem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasexamltem getInfoById(String id);

}

