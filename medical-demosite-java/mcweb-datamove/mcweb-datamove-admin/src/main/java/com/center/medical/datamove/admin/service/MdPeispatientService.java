package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdPeispatient;

import java.util.List;

/**
 * QT体检者表(MdPeispatient)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:53:31
 */
public interface MdPeispatientService extends IService<MdPeispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatient> getPage(PageParam<MdPeispatient> page, MdPeispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatient getInfoById(String id);

    /**
     * 批量保存体检者
     *
     * @param mapAsList
     */
    void saOrUpList(List<MdPeispatient> mapAsList);

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    MdPeispatient getByPatientCode(String patientCode);
}

