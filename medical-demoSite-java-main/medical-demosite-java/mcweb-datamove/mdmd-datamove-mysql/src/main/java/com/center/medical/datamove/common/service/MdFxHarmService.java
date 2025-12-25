package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFxHarm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 职业团检分析-危害因素(MdFxHarm)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
public interface MdFxHarmService extends IService<MdFxHarm> {

    /**
     * 分页查询[职业团检分析-危害因素]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFxHarm> getPage(PageParam<MdFxHarm> page, MdFxHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxHarm getInfoById(String id);

}

