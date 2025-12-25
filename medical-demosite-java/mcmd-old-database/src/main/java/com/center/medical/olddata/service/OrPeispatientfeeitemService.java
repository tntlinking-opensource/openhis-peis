package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientfeeitem;

import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:55:01
 */
public interface OrPeispatientfeeitemService extends IService<OrPeispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientfeeitem> getPage(PageParam<OrPeispatientfeeitem> page, OrPeispatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientfeeitem getInfoById(String id);

    /**
     * 通过体检号获取收费项目
     *
     * @param patientcode
     * @return
     */
    List<OrPeispatientfeeitem> getByPatientCode(String patientcode);
}

