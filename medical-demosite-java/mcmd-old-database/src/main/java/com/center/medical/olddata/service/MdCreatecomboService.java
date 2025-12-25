package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdCreatecombo;

import java.util.List;

/**
 * 最小套餐(MdCreatecombo)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:58:13
 */
public interface MdCreatecomboService extends IService<MdCreatecombo> {

    /**
     * 分页查询[最小套餐]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCreatecombo> getPage(PageParam<MdCreatecombo> page, MdCreatecombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreatecombo getInfoById(String id);

    /**
     * 添加或修改
     *
     * @param map
     */
    void saOrUp(MdCreatecombo map);

    /**
     * 批量添加或更新
     * @param mdCreatecomboList
     */
    void saOrUpList(List<MdCreatecombo> mdCreatecomboList);
}

