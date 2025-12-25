package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.EmphasisAskSymptom;

import java.util.List;

/**
 * JC重点询问症状表(EmphasisAskSymptom)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:14
 */
public interface EmphasisAskSymptomService extends IService<EmphasisAskSymptom> {

    /**
     * 分页查询[JC重点询问症状表]列表
     *
     * @param page  分页参数
     * @param param EmphasisAskSymptom查询参数
     * @return 分页数据
     */
    IPage<EmphasisAskSymptom> getList(PageParam<EmphasisAskSymptom> page, EmphasisAskSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    EmphasisAskSymptom getInfoById(String id);


    /**
     * 保存或修改数据
     * @param emp
     * @return
     */
    String saveOrUpdateEmphasis(List<EmphasisAskSymptom> emp);

    /**
     * 保存单个
     * @param emp
     * @return
     */
    String saveEdit(EmphasisAskSymptom emp);

    /**
     * 删除
     * @param ids
     * @return
     */
    String removeEmp(String ids);

    /**
     * 同步
     * @return
     */
    String synchonize();
}

