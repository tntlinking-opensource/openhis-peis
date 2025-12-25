package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdCreatemeal;

import java.util.List;

/**
 * 普通套餐表(MdCreatemeal)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:57:52
 */
public interface MdCreatemealService extends IService<MdCreatemeal> {

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCreatemeal> getPage(PageParam<MdCreatemeal> page, MdCreatemeal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreatemeal getInfoById(String id);

    /**
     * 添加或修改
     *
     * @param map
     */
    void saOrUp(MdCreatemeal map);

    /**
     *  批量添加或更新
     * @param mdCreatemealList
     */
    void saOrUpList(List<MdCreatemeal> mdCreatemealList);
}

