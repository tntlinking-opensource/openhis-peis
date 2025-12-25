package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientarchive;

/**
 * 体检者档案表(MdPeispatientarchive)服务接口
 *
 * @author ay
 * @since 2023-09-04 09:16:14
 */
public interface OrPeispatientarchiveService extends IService<OrPeispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientarchive> getPage(PageParam<OrPeispatientarchive> page, OrPeispatientarchive param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientarchive getInfoById(String id);

}

