package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrLung;

import java.util.List;

/**
 * KS肺功能(Lung)服务接口
 *
 * @author ay
 * @since 2024-06-05 15:44:03
 */
public interface OrLungService extends IService<OrLung> {

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrLung> getPage(PageParam<OrLung> page, OrLung param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrLung getInfoById(String id);

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    List<OrLung> getListByPatientCode(String patientCode);
}

