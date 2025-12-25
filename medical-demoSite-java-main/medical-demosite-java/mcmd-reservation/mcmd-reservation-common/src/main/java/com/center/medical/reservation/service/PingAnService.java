package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.PaPeissortexam;
import com.center.medical.reservation.bean.dto.GetPingAnCodeDto;
import com.center.medical.reservation.bean.param.PingAnNumsParam;
import com.center.medical.reservation.bean.vo.ApplyForOrderVo;
import com.center.medical.reservation.bean.vo.PingAnNumsVo;

import java.util.List;

/**
 * 平安软件-排检(PaPeissortexam)服务接口
 *
 * @author ay
 * @since 2023-10-20 13:51:13
 */
public interface PingAnService extends IService<PaPeissortexam> {

    /**
     * 分页查询[平安软件-排检]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PaPeissortexam> getPage(PageParam<PaPeissortexam> page, PaPeissortexam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PaPeissortexam getInfoById(String id);

    /**
     * 体检预约可用人数
     * @param param
     * @return
     */
    List<PingAnNumsVo> getAvailableNums(PingAnNumsParam param);

    /**
     * 申请预约平安
     * @param data
     * @param countreportoccupation
     * @return
     */
    ApplyForOrderVo applyForOrder(String data,Integer countreportoccupation);

    /**
     * 取消申请
     * @param hospitalOrderId
     * @param orderId
     * @param countreportoccupation
     */
    void cancelOrder(String hospitalOrderId, String orderId, int countreportoccupation);


    /**
     * 修改预约
     * @param hospitalOrderId
     * @param orderId
     * @param appointmentTime
     * @param countreportoccupation
     */
    void changeDate(String hospitalOrderId, String orderId, String appointmentTime, int countreportoccupation);


    /**
     * 客户授权查看影像报告
     * @param hospitalOrderId
     * @param orderId
     */
    void authorize(String hospitalOrderId, String orderId);

    /**
     * 到检确认
     * @param id
     * @param captcha
     * @return
     */
    void confirmOrder(String id, String captcha);

    /**
     * 获取平安好医生需要上传pdf的体检号
     * @return
     */
    List<GetPingAnCodeDto> getPingAnCode();
}

