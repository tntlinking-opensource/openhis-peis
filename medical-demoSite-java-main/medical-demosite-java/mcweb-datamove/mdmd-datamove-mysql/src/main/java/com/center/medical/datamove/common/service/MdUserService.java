package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检用户账号(MdUser)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:16
 */
public interface MdUserService extends IService<MdUser> {

    /**
     * 分页查询[体检用户账号]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdUser> getPage(PageParam<MdUser> page, MdUser param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    MdUser getInfoById(String id);

}

