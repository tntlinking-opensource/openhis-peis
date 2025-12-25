package com.center.medical.reservation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.ReAnalysisParam;
import com.center.medical.reservation.bean.param.RightPageParam;
import com.center.medical.reservation.bean.vo.RARightPageVo;
import com.center.medical.reservation.bean.vo.RATodayVo;
import com.center.medical.reservation.bean.vo.ReAnalysisVo;
import com.center.medical.reservation.dao.ReservationAnalysisMapper;
import com.center.medical.reservation.service.ReservationAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检预约记录(Reservation)服务实现类
 *
 * @author ay
 * @since 2024-03-29 08:58:17
 */
@Slf4j
@Service("reservationAnalysis")
@RequiredArgsConstructor
public class ReservationAnalysisServiceImpl extends ServiceImpl<ReservationAnalysisMapper, Reservation> implements ReservationAnalysisService {

    private final ReservationAnalysisMapper reservationAnalysisMapper;

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param Reservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReAnalysisVo> getPage(PageParam<ReAnalysisVo> page, ReAnalysisParam param) {
        return reservationAnalysisMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Reservation getInfoById(String id) {
        return reservationAnalysisMapper.getInfoById(id);
    }


    /**
     * 右侧页面分页
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<RARightPageVo> rightPage(PageParam<RARightPageVo> page, RightPageParam param) {
        return reservationAnalysisMapper.rightPage(page,param);
    }

    /**
     * 导出左侧数据
     * @param param
     * @return
     */
    @Override
    public List<ReAnalysisVo> exportPage(ReAnalysisParam param) {
        return reservationAnalysisMapper.exportPage(param);
    }

    /**
     * 导出右侧分页
     * @param param
     * @return
     */
    @Override
    public List<RARightPageVo> exportRightPage(RightPageParam param) {
        return reservationAnalysisMapper.exportRightPage(param);
    }


    /**
     * 今日情况
     * @param param
     * @return
     */
    @Override
    public RATodayVo getToday(ReAnalysisParam param) {
        return reservationAnalysisMapper.getToday(param);
    }
}

