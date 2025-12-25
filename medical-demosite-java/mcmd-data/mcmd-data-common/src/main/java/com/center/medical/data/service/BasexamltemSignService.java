package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.bean.param.FeatureListParam;
import com.center.medical.data.bean.vo.FeatureListVo;

/**
 * JC检查项目体证词关联表(BasexamltemSign)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:24
 */
public interface BasexamltemSignService extends IService<BasexamltemSign> {

    /**
     * 分页查询[JC检查项目体证词关联表]列表
     *
     * @param page  分页参数
     * @param param BasexamltemSign查询参数
     * @return 分页数据
     */
    IPage<BasexamltemSign> getList(PageParam<BasexamltemSign> page, BasexamltemSign param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BasexamltemSign getInfoById(String id);

    /**
     * 根据检查项目id删除体征词
     *
     * @param inspectId 检查项目id
     */
    void removeByInspectId(String inspectId);

    /**
     * 根据检查项目ID获取相对应的体征词
     * @param page
     * @param param
     * @return
     */
    IPage<FeatureListVo> getFeatureListData(PageParam<FeatureListVo> page, FeatureListParam param);
}

