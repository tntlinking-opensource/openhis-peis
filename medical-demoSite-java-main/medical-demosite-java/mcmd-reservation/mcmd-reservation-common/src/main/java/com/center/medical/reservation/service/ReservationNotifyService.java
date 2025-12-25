package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.dto.ReservationNotifyDto;
import com.center.medical.reservation.bean.model.ReservationNotify;

import java.util.List;

/**
 * 预约信息变更通知记录(ReservationNotify)服务接口
 *
 * @author makejava
 * @since 2023-09-21 10:56:58
 */
public interface ReservationNotifyService extends IService<ReservationNotify> {

    /**
     * 分页查询[预约信息变更通知记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReservationNotify> getPage(PageParam<ReservationNotify> page, ReservationNotify param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationNotify getInfoById(String id);

    /**
     * 获取待通知的列表
     *
     * @param num 数量
     * @return
     */
    List<ReservationNotifyDto> getNotifyList(Integer num);
}

