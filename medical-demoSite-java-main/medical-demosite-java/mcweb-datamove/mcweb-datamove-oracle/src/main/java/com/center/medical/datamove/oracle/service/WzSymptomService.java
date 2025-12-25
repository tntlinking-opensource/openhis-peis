package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.WzSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——症状(WzSymptom)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:51
 */
public interface WzSymptomService extends IService<WzSymptom> {

    /**
     * 分页查询[KS问诊——症状]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WzSymptom> getPage(PageParam<WzSymptom> page, WzSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzSymptom getInfoById(String id);

}

