package com.center.medical.reception.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.*;
import com.center.medical.reception.bean.param.MyOrderParam;
import com.center.medical.reception.bean.param.UnitReservationParam;
import com.center.medical.reception.dao.NewReservationMapper;
import com.center.medical.reception.service.NewReservationService;
import com.center.medical.reception.service.OrderService;
import com.center.medical.reservation.bean.dto.AppointmentDto;
import com.center.medical.reservation.bean.model.ReservationGroupCode;
import com.center.medical.reservation.service.ReservationGroupCodeService;
import com.center.medical.reservation.service.ReservationOpenApiService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.service.PeispatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2024-03-13 09:54:40
 */
@Slf4j
@Service("newReservationService")
@RequiredArgsConstructor
public class NewReservationServiceImpl extends ServiceImpl<NewReservationMapper, Peispatient> implements NewReservationService {

    private final NewReservationMapper newReservationMapper;
    private final PeispatientService peispatientService;
    private final ReservationGroupCodeService reservationGroupCodeService;
    private final OrderService orderService;
    private final ReservationOpenApiService reservationOpenApiService;
    private final ReservationService reservationService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient> getPage(PageParam<Peispatient> page, Peispatient param) {
        return newReservationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return newReservationMapper.getInfoById(id);
    }

    /**
     * 首页预约列表
     * @param phone
     * @return
     */
    @Override
    public List<HomePageListDto> getHomePageList(String phone) {
        return newReservationMapper.getHomePageList(phone);
    }


    /**
     * 获取用户档案信息
     * @param phone
     * @return
     */
    @Override
    public List<UserIdcardDto> getUserIdcard(String phone) {
        return newReservationMapper.getUserIdcard(phone);
    }

    /**
     * 获取体检者数据
     * @param patientcode
     * @return
     */
    @Override
    public GetNewReDataDto getNewReData(String patientcode) {
        //获取体检者数据
        GetNewReDataDto dto = newReservationMapper.getNewReData(patientcode);
        //检查项目
        List<NewReItemsDto> itemData = newReservationMapper.getItemData(patientcode);
        dto.setItems(itemData);
        dto.setItemsSize(itemData.size());
        //合计数据
        Double priceTotal = 0d;
        Double factpriceTotal = 0d;
        for (NewReItemsDto newReItemsDto : itemData) {
            priceTotal = Arith.add(priceTotal,newReItemsDto.getPrice());
            factpriceTotal = Arith.add(factpriceTotal,newReItemsDto.getFactprice());
        }
        dto.setPriceTotal(priceTotal);
        dto.setFactpriceTotal(factpriceTotal);
        if (StringUtils.isNotEmpty(dto.getNumorgresv())){
            List<OrderFzxDto> orderFzxDtoList = newReservationMapper.getOrderFzx(dto.getNumorgresv());
            dto.setOrderFzxDtoList(orderFzxDtoList);
        }

        return dto;
    }


    /**
     * 单位预约查询单位
     * @param name
     * @return
     */
    @Override
    public IPage<QueryUnitDto> queryUnit(PageParam<QueryUnitDto> page, String name) {
        return newReservationMapper.queryUnit(page,name);
    }


    /**
     * 单位预约提交
     * @param param
     * @return
     */
    @Override
    public String unitReservation(UnitReservationParam param) {
        if (StringUtils.isEmpty(param.getExtractedCode())
                || StringUtils.isEmpty(param.getPatientname())
                || StringUtils.isEmpty(param.getIdOrg())
                || StringUtils.isEmpty(param.getPhone())
        ){
            throw new ServiceException("请填写完整数据！");
        }
        ReservationGroupCode reservationGroupCode = reservationGroupCodeService.getOne(new LambdaQueryWrapper<ReservationGroupCode>()
                .eq(ReservationGroupCode::getIdOrg, param.getIdOrg())
                .eq(ReservationGroupCode::getExtractedCode, param.getExtractedCode())
                .eq(ReservationGroupCode::getStatus, 0)
        );
        if (ObjectUtils.isEmpty(reservationGroupCode)){
            throw new ServiceException("请确认公司或预约码是否正确！");
        }
        param.setGroupId(reservationGroupCode.getGroupId());
        String patientcode = orderService.unitReservation(param);
        return patientcode;
    }


    /**
     * 帮人预约
     * @param phone
     * @return
     */
    @Override
    public List<HelpAppointDto> helpAppoint(String phone) {
        return newReservationMapper.helpAppoint(phone);
    }

    /**
     * 我的订单
     * @param param
     * @return
     */
    @Override
    public IPage<MyOrderDto> myOrder(PageParam<MyOrderDto> page, MyOrderParam param) {
        return newReservationMapper.myOrder(page,param);
    }


    /**
     * 订单角标
     * @param phone
     * @return
     */
    @Override
    public OrderMarkersDto orderMarkers(String phone) {
        OrderMarkersDto dto = new OrderMarkersDto();
        dto.setPendingAppointment(newReservationMapper.orderMarkers(phone,"1"));
        dto.setPendingTest(newReservationMapper.orderMarkers(phone,"2"));
        return dto;
    }


    /**
     * 立即预约
     * @param data
     * @return
     */
    @Override
    public String appointmentNow(AppointmentDto data) {
        //更新体检者信息，证件类型和身份证,已传过来的为准
        Peispatient peispatient = peispatientService.getByPatientCode(data.getPatientcode());
        String reservationNo;
        try {
            if (data.getFUsecodehiden() == 1) {
                //团检
                if (StringUtils.isBlank(data.getPatientcode()) || StringUtils.isBlank(data.getIdOrg())) {
                    throw new ServiceException("参数有误！");
                }
                reservationNo = reservationService.check(data.getNumorgresv(), data.getPatientcode(), data.getFUsecodehiden());
                data.setIdOrg(peispatient.getIdOrg());
                if (StringUtils.isBlank(reservationNo)) {
                    //可预约
                    reservationNo = reservationOpenApiService.apply(data);
                } else {
                    //已预约
                    throw new ServiceException("您已预约过了，不能重复预约！");
                }
            } else {
                reservationNo = reservationOpenApiService.personApply(data);
            }
            
            // 更新体检者信息，使用独立事务避免死锁
            updatePatientInfo(peispatient, data);
            
            return reservationNo;
        } catch (ServiceException e) {
            // 业务异常直接抛出
            throw e;
        } catch (Exception e) {
            log.error("预约处理异常: {}", e.getMessage(), e);
            throw new ServiceException("预约失败，系统繁忙请稍后重试！");
        }
    }
    
    /**
     * 更新体检者信息，使用独立事务
     * @param peispatient 体检者信息
     * @param data 预约数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePatientInfo(Peispatient peispatient, AppointmentDto data) {
        peispatient.setPatientname(data.getRealName());
        peispatient.setIdcardno(data.getIdcard());
        peispatient.setCountreportoccupationxml(data.getCountreportoccupationxml());
        peispatient.setFIsforreserve(1);
        peispatient.setFIsforprepare(1);
        peispatient.setDateguidancereturned(data.getReservationDate());
        peispatientService.updateById(peispatient);
    }

    /**
     * 检查体检号和手机号是否相符合
     * @param patientcode
     * @param phone
     * @return
     */
    @Override
    public Boolean checkPatientcode(String patientcode, String phone) {
        if (patientcode.length() < 8){
            return Boolean.FALSE;
        }
        //有的地方传过来的是长号, 截取8位
        patientcode = patientcode.substring(patientcode.length() - 8);
        long count = peispatientService.checkPatientcode(patientcode, phone);
        if (count>0){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }
}

