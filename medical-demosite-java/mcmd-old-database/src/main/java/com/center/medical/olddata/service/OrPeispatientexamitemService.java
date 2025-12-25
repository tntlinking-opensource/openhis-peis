package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientexamitem;

import java.util.List;

/**
 * LIS结果(Peispatientexamitem)服务接口
 *
 * @author ay
 * @since 2024-06-05 15:00:11
 */
public interface OrPeispatientexamitemService extends IService<OrPeispatientexamitem> {

    /**
     * 分页查询[LIS结果]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientexamitem> getPage(PageParam<OrPeispatientexamitem> page, OrPeispatientexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientexamitem getInfoById(String id);

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    List<OrPeispatientexamitem> getListByPatientCode(String patientCode);
}

