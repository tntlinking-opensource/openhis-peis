package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdTjbbGmd;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS骨密度体检报表(MdTjbbGmd)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:02
 */
public interface MdTjbbGmdService extends IService<MdTjbbGmd> {

    /**
     * 分页查询[KS骨密度体检报表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTjbbGmd> getPage(PageParam<MdTjbbGmd> page, MdTjbbGmd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjbbGmd getInfoById(String id);

}

