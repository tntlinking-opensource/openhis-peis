package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdUserauthcode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 用户授权码(MdUserauthcode)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:27
 */
public interface MdUserauthcodeService extends IService<MdUserauthcode> {

    /**
     * 分页查询[用户授权码]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdUserauthcode> getPage(PageParam<MdUserauthcode> page, MdUserauthcode param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUserauthcode getInfoById(String id);

}

