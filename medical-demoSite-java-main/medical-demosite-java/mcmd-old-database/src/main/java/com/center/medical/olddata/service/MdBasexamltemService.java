package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdBasexamltem;

/**
 * JC检查项目表(MdBasexamltem)服务接口
 *
 * @author ay
 * @since 2024-07-13 13:49:09
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

