package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFylx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC费用类型(MdFylx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
public interface MdFylxService extends IService<MdFylx> {

    /**
     * 分页查询[JC费用类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFylx> getPage(PageParam<MdFylx> page, MdFylx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFylx getInfoById(String id);

}

