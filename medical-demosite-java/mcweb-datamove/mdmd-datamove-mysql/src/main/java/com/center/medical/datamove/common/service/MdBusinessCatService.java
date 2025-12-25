package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBusinessCat;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 业务类型(MdBusinessCat)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
public interface MdBusinessCatService extends IService<MdBusinessCat> {

    /**
     * 分页查询[业务类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBusinessCat> getPage(PageParam<MdBusinessCat> page, MdBusinessCat param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    MdBusinessCat getInfoById(Object id);

}

