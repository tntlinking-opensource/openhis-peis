package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdTjbbXyjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS血压检测(MdTjbbXyjc)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:04
 */
public interface MdTjbbXyjcService extends IService<MdTjbbXyjc> {

    /**
     * 分页查询[KS血压检测]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTjbbXyjc> getPage(PageParam<MdTjbbXyjc> page, MdTjbbXyjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjbbXyjc getInfoById(String id);

}

