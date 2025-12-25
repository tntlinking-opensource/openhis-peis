package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdMemberbirthdat;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 会员生日提醒回访表(MdMemberbirthdat)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
public interface MdMemberbirthdatService extends IService<MdMemberbirthdat> {

    /**
     * 分页查询[会员生日提醒回访表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdMemberbirthdat> getPage(PageParam<MdMemberbirthdat> page, MdMemberbirthdat param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdMemberbirthdat getInfoById(String id);

}

