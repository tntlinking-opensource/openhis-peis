package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdUserLevelBind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 会员当前的等级(MdUserLevelBind)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:20
 */
public interface MdUserLevelBindService extends IService<MdUserLevelBind> {

    /**
     * 分页查询[会员当前的等级]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdUserLevelBind> getPage(PageParam<MdUserLevelBind> page, MdUserLevelBind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userLevelId
     * @return 详情信息
     */
    MdUserLevelBind getInfoById(Long id);

}

