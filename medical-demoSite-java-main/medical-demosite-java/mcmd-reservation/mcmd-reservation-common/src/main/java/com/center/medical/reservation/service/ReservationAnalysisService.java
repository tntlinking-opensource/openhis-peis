package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.ReAnalysisParam;
import com.center.medical.reservation.bean.param.RightPageParam;
import com.center.medical.reservation.bean.vo.RARightPageVo;
import com.center.medical.reservation.bean.vo.RATodayVo;
import com.center.medical.reservation.bean.vo.ReAnalysisVo;

import java.util.List;

/**
 * 体检预约记录(Reservation)服务接口
 *
 * @author ay
 * @since 2024-03-29 08:58:17
 */
public interface ReservationAnalysisService extends IService<Reservation> {

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReAnalysisVo> getPage(PageParam<ReAnalysisVo> page, ReAnalysisParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Reservation getInfoById(String id);

    /**
     * 右侧页面分页
     * @param page
     * @param param
     * @return
     */
    IPage<RARightPageVo> rightPage(PageParam<RARightPageVo> page, RightPageParam param);

    /**
     * 导出左侧数据
     * @param param
     * @return
     */
    List<ReAnalysisVo> exportPage(ReAnalysisParam param);

    /**
     * 导出右侧分页
     * @param param
     * @return
     */
    List<RARightPageVo> exportRightPage(RightPageParam param);

    /**
     * 今日情况
     * @param param
     * @return
     */
    RATodayVo getToday(ReAnalysisParam param);
}

