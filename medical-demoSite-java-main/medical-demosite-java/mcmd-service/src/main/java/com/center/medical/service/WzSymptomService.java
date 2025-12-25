package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——症状(WzSymptom)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:27
 */
public interface WzSymptomService extends IService<WzSymptom> {

    /**
     * 分页查询[KS问诊——症状]列表
     *
     * @param page  分页参数
     * @param param WzSymptom查询参数
     * @return 分页数据
     */
    IPage<WzSymptom> getList(PageParam<WzSymptom> page, WzSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzSymptom getInfoById(String id);

}

