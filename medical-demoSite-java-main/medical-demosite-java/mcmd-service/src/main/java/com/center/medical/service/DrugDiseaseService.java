package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.DrugDisease;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 禁忌疾病(DrugDisease)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:49
 */
public interface DrugDiseaseService extends IService<DrugDisease> {

    /**
     * 分页查询[禁忌疾病]列表
     *
     * @param page  分页参数
     * @param param DrugDisease查询参数
     * @return 分页数据
     */
    IPage<DrugDisease> getList(PageParam<DrugDisease> page, DrugDisease param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugDisease getInfoById(String id);

    /**
     * 根据条件分页获取
     * @param page
     * @param key
     * @return
     */
    IPage<DrugDisease> getJjjbData(PageParam<DrugDisease> page, String key);
}

