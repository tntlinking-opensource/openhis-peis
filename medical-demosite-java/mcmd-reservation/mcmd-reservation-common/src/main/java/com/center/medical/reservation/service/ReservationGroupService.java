package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.dto.ReservationGroupData;
import com.center.medical.reservation.bean.model.ReservationGroup;
import com.center.medical.reservation.bean.param.GetReVipNumberParam;
import com.center.medical.reservation.bean.param.ReservationGroupParam;
import com.center.medical.reservation.bean.param.RgListParam;
import com.center.medical.reservation.bean.vo.ReVipNumberVo;

import java.util.List;

/**
 * 团体预约记录(ReservationGroup)服务接口
 *
 * @author makejava
 * @since 2023-08-31 16:45:54
 */
public interface ReservationGroupService extends IService<ReservationGroup> {

    /**
     * 分页查询[团体预约记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReservationGroup> getPage(PageParam<ReservationGroup> page, RgListParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationGroup getInfoById(String id);

    /**
     * 新增预约
     *
     * @param param 实体对象
     * @return 预约号
     */
    Boolean saOrUp(ReservationGroupParam param);

    /**
     * 删除团检预约
     *
     * @param ids 对象id集合
     * @return
     */
    Boolean rmByIds(List<String> ids);

    /**
     * 获取团队预约信息导出数据
     *
     * @param param
     */
    List<ReservationGroupData> getExportData(RgListParam param);

    /**
     * 获取可预约vip人数
     * @param param
     * @return
     */
    List<ReVipNumberVo> getReVipNumber(GetReVipNumberParam param);
}

