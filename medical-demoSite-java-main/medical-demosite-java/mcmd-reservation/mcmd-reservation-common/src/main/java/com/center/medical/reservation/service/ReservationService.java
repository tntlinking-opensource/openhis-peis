package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.dto.RGroupData;
import com.center.medical.reservation.bean.dto.ReservationData;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.model.ReservationGroup;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.reservation.bean.vo.ReservationVo;
import com.center.medical.reservation.bean.vo.VipExportVo;

import java.util.List;

/**
 * 体检预约记录(Reservation)表服务接口
 *
 * @author ay
 * @since 2023-03-18 08:54:14
 */
public interface ReservationService extends IService<Reservation> {

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReservationVo> getList(PageParam<Reservation> page, ReservationParam param);

    /**
     * 根据预约号获取记录详情
     *
     * @param reservationNo 预约号
     * @return 详情信息
     */
    ReservationVo getInfoByNo(String reservationNo);

    /**
     * 新增预约
     *
     * @param reservation 实体对象
     * @return 预约号
     */
    R<String> saOrUp(Reservation reservation);

    /**
     * 删除预约记录
     *
     * @param ids 删除对象id集合
     * @return
     */
    Boolean rmByIds(List<String> ids);

    /**
     * 检查是否可以预约
     *
     * @param numorgresv    订单号
     * @param patientcode   体检号（团检才有）
     * @param fUsecodehiden 订单类型：0.个检 1.团检
     * @return
     */
    String check(String numorgresv, String patientcode, Integer fUsecodehiden);

    /**
     * 根据id查体检预约记录详情
     *
     * @param id
     * @return
     */
    ReservationVo getInfoById(String id);

    /**
     * 批量修改日期及人员类型
     *
     * @param param
     * @return
     */
    Boolean batchModify(BatchModifyParam param);

    /**
     * 新增团体预约记录
     *
     * @param reservationGroup
     * @return
     */
    Boolean addGroupReservation(ReservationGroup reservationGroup);

    /**
     * 更新预约记录的状态
     *
     * @param reservation
     * @param flag        是否需要发送通知请求：0.不需要 1.需要
     * @return
     */
    Boolean updateStatus(Reservation reservation, Boolean flag);

    /**
     * 获取导出预约数据
     *
     * @param param
     */
    List<ReservationData> getExportData(ReservationParam param);

    /**
     * 团体预约信息按时间段导出数据
     *
     * @param param
     * @return
     */
    List<RGroupData> getGroupExportData(RgListParam param);

    /**
     * 根据预约号或体检号获取预约详情
     * @param param
     * @return
     */
    Reservation getReservation(GetReservationParam param);

    /**
     * 完成预约并发送预约短信
     * @param reservation
     * @return
     */
    R<String> saOrUpReservation(Reservation reservation);

    /**
     * vip及贵宾导出
     * @param param
     * @return
     */
    List<VipExportVo> vipExport(VipExportParam param);
}

