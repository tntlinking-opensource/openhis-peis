package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdTjreg;

/**
 * KS一般检查(MdTjreg)服务接口
 *
 * @author ay
 * @since 2024-06-05 16:02:18
 */
public interface MdTjregService extends IService<MdTjreg> {

    /**
     * 分页查询[KS一般检查]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTjreg> getPage(PageParam<MdTjreg> page, MdTjreg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjreg getInfoById(String id);

}

