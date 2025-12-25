package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.SortQueryParam;
import com.center.medical.reservation.bean.vo.SortQueryVo;

import java.util.List;

/**
 * 体检预约记录(Reservation)服务接口
 *
 * @author ay
 * @since 2024-01-18 17:12:03
 */
public interface SortQueryService extends IService<Reservation> {

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SortQueryVo> getPage(PageParam<SortQueryVo> page, SortQueryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Reservation getInfoById(String id);

    /**
     * 导出项目预约明细数据
     * @param param
     * @return
     */
    List<SortQueryVo> exportData(SortQueryParam param);
}

