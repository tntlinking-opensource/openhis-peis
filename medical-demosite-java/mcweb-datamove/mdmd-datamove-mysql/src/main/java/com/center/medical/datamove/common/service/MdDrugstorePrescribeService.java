package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdDrugstorePrescribe;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 开药记录(MdDrugstorePrescribe)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:15
 */
public interface MdDrugstorePrescribeService extends IService<MdDrugstorePrescribe> {

    /**
     * 分页查询[开药记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdDrugstorePrescribe> getPage(PageParam<MdDrugstorePrescribe> page, MdDrugstorePrescribe param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrugstorePrescribe getInfoById(String id);

}

