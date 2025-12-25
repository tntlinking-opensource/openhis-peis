package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Chest;
import com.center.medical.bean.param.ChestParam;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 订单柜子信息(Chest)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-06 08:59:22
 */
public interface ChestService extends IService<Chest> {

    /**
     * 分页查询[订单柜子信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Chest> getPage(PageParam<Chest> page, ChestParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Chest getInfoById(String id);

    /**
     *保存或更新
     * @param chest
     * @return
     */
    Boolean saOrUp(Chest chest);

    /**
     * 获取订单柜子信息导出数据
     * @param chest
     * @return
     */
    List<Chest> getExportData(Chest chest);
}

