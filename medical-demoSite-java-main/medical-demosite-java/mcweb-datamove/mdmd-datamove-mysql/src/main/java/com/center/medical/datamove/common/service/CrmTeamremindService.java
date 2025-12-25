package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmTeamremind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 客户预检跟踪表(CrmTeamremind)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:02
 */
public interface CrmTeamremindService extends IService<CrmTeamremind> {

    /**
     * 分页查询[客户预检跟踪表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmTeamremind> getPage(PageParam<CrmTeamremind> page, CrmTeamremind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmTeamremind getInfoById(String id);

}

