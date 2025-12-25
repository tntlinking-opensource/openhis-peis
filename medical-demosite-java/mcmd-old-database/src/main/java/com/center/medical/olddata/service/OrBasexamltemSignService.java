package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrBasexamltemSign;

import java.util.List;

/**
 * JC检查项目体证词关联表(BasexamltemSign)服务接口
 *
 * @author ay
 * @since 2024-07-13 14:27:48
 */
public interface OrBasexamltemSignService extends IService<OrBasexamltemSign> {

    /**
     * 分页查询[JC检查项目体证词关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrBasexamltemSign> getPage(PageParam<OrBasexamltemSign> page, OrBasexamltemSign param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrBasexamltemSign getInfoById(String id);

    /**
     * 通过检查项目id查询
     * @param inspectId
     * @return
     */
    List<OrBasexamltemSign> getInfoByInspectId(String inspectId);
}

