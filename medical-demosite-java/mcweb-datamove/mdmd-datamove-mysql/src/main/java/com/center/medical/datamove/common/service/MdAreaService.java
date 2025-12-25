package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdArea;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 籍贯表(MdArea)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
public interface MdAreaService extends IService<MdArea> {

    /**
     * 分页查询[籍贯表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdArea> getPage(PageParam<MdArea> page, MdArea param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdArea getInfoById(String id);

}

