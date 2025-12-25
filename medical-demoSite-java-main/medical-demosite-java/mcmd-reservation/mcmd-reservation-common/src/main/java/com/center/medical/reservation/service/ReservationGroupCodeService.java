package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationGroupCode;
import com.center.medical.reservation.bean.param.GenerateGroupCodeParam;
import com.center.medical.reservation.bean.param.ModifyGroupCodeParam;
import com.center.medical.reservation.bean.param.ReGroupCodePageParam;
import com.center.medical.reservation.bean.vo.GenerateGroupCodeVo;

/**
 * 团体预约分享码(ReservationGroupCode)服务接口
 *
 * @author ay
 * @since 2024-03-08 16:38:40
 */
public interface ReservationGroupCodeService extends IService<ReservationGroupCode> {

    /**
     * 分页查询[团体预约分享码]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReservationGroupCode> getPage(PageParam<ReservationGroupCode> page, ReGroupCodePageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationGroupCode getInfoById(String id);

    /**
     * 生成团检分享码
     * @param param
     * @return
     */
    GenerateGroupCodeVo generateGroupCode(GenerateGroupCodeParam param);

    /**
     * 修改团检分享码
     * @param param
     * @return
     */
    Boolean modifyGroupCode(ModifyGroupCodeParam param);
}

