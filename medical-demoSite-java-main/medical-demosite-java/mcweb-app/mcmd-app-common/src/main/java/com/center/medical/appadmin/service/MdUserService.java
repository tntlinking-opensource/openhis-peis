package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.MdUser;
import com.center.medical.appadmin.bean.param.AppUserParam;
import com.center.medical.appadmin.bean.vo.AppUserVo;
import com.center.medical.common.utils.page.PageParam;

/**
 * 用户表(MdUser)服务接口
 *
 * @author ay
 * @since 2024-07-23 17:03:25
 */
public interface MdUserService extends IService<MdUser> {

    /**
     * 分页查询[用户表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppUserVo> getPage(PageParam<AppUserVo> page, AppUserParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    MdUser getInfoById(String id);

}

