package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdDrugDisease;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * jc禁忌疾病(MdDrugDisease)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
public interface MdDrugDiseaseService extends IService<MdDrugDisease> {

    /**
     * 分页查询[jc禁忌疾病]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdDrugDisease> getPage(PageParam<MdDrugDisease> page, MdDrugDisease param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrugDisease getInfoById(String id);

}

