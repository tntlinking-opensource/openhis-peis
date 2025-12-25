package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPatientUsers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检用户账号(MdPatientUsers)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
public interface MdPatientUsersService extends IService<MdPatientUsers> {

    /**
     * 分页查询[体检用户账号]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPatientUsers> getPage(PageParam<MdPatientUsers> page, MdPatientUsers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPatientUsers getInfoById(String id);

}

