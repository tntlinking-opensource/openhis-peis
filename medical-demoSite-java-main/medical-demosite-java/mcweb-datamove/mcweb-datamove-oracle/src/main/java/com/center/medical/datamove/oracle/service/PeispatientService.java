package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.Peispatient;

import java.util.List;

/**
 * (Peispatient)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:09
 */
public interface PeispatientService extends IService<Peispatient> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getPage(PageParam<Peispatient> page, Peispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 通过分组id获取体检者
     *
     * @param groupId
     * @return
     */
    List<Peispatient> getByGroupId(String groupId);
}

