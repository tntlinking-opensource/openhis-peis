package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.*;
import com.center.medical.reception.bean.param.MyOrderParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2024-03-13 09:54:40
 */
public interface NewReservationMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getPage(PageParam<Peispatient> page, @Param("param") Peispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 首页预约列表
     * @param phone
     * @return
     */
    List<HomePageListDto> getHomePageList(@Param("phone") String phone);

    /**
     * 获取用户档案信息
     * @param phone
     * @return
     */
    List<UserIdcardDto> getUserIdcard(@Param("phone") String phone);

    /**
     * 获取体检者数据
     * @param patientcode
     * @return
     */
    GetNewReDataDto getNewReData(@Param("patientcode") String patientcode);

    /**
     * 获取检查项目数据
     * @param patientcode
     * @return
     */
    List<NewReItemsDto> getItemData(@Param("patientcode") String patientcode);

    /**
     * 单位预约查询单位
     * @param name
     * @return
     */
    IPage<QueryUnitDto> queryUnit(PageParam<QueryUnitDto> page, @Param("name") String name);

    /**
     * 帮人预约
     * @param phone
     * @return
     */
    List<HelpAppointDto> helpAppoint(@Param("phone") String phone);

    /**
     * 我的订单
     * @param param
     * @return
     */
    IPage<MyOrderDto> myOrder(PageParam<MyOrderDto> page, @Param("param") MyOrderParam param);

    /**
     * 订单角标
     * @param phone
     * @return
     */
    Integer orderMarkers(@Param("phone") String phone, @Param("status") String status);

    /**
     * 获取订单和分中心数据
     * @param numorgresv
     * @return
     */
    List<OrderFzxDto> getOrderFzx(@Param("numorgresv") String numorgresv);
}
