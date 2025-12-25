package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdQyjjlx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 企业经济类型(MdQyjjlx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
public interface MdQyjjlxService extends IService<MdQyjjlx> {

    /**
     * 分页查询[企业经济类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdQyjjlx> getPage(PageParam<MdQyjjlx> page, MdQyjjlx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdQyjjlx getInfoById(String id);

}

