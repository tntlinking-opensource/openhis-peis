package com.center.medical.reservation.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.dto.ReservationGroupData;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.model.ReservationGroup;
import com.center.medical.reservation.bean.model.ReservationGroupTime;
import com.center.medical.reservation.bean.param.GetReVipNumberParam;
import com.center.medical.reservation.bean.param.ReservationGroupParam;
import com.center.medical.reservation.bean.param.RgListParam;
import com.center.medical.reservation.bean.vo.ReVipNumberVo;
import com.center.medical.reservation.dao.ReservationGroupMapper;
import com.center.medical.reservation.service.ReservationGroupService;
import com.center.medical.reservation.service.ReservationGroupTimeService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 团体预约记录(ReservationGroup)服务实现类
 *
 * @author makejava
 * @since 2023-08-31 16:45:54
 */
@Slf4j
@Service("reservationGroupService")
@RequiredArgsConstructor
public class ReservationGroupServiceImpl extends ServiceImpl<ReservationGroupMapper, ReservationGroup> implements ReservationGroupService {

    private final ReservationGroupMapper reservationGroupMapper;
    private final ReservationSettingService reservationSettingService;
    private final ReservationService reservationService;
    private final MapperFacade mapperFacade;
    private final CreateorderService createorderService;
    private final Snowflake snowflake;
    private final ISysConfigService iSysConfigService;
    private final ReservationGroupTimeService reservationGroupTimeService;

    /**
     * 分页查询[团体预约记录]列表
     *
     * @param page  分页参数
     * @param param ReservationGroup查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReservationGroup> getPage(PageParam<ReservationGroup> page, RgListParam param) {
        return reservationGroupMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReservationGroup getInfoById(String id) {
        return reservationGroupMapper.getInfoById(id);
    }

    public static void main(String[] args) {
        DateTime dateTime = DateUtil.parse("2023-09-03 00:00:00", "yyyy-MM-dd HH:mm:ss");
        System.out.println(dateTime);
        DateTime dateTime2 = DateUtil.parse("2023-09-05 00:00:00", "yyyy-MM-dd HH:mm:ss");
        System.out.println(dateTime2);
        long l = DateUtil.betweenDay(dateTime, dateTime2, true) + 1;
        for (int i = 0; i < l; i++) {
            System.out.println(DateUtil.offsetDay(dateTime, i));
        }
    }

    /**
     * 新增预约
     *
     * @param param 实体对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(ReservationGroupParam param) {
        SysUser admin = SecurityUtils.getLoginUser().getUser();
        //查询订单
        if (StringUtils.isBlank(param.getId())) {
            ReservationGroup reservationGroup = mapperFacade.map(param, ReservationGroup.class);
            Createorder order = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getDdh, reservationGroup.getOrderNum()));
            long days = DateUtil.betweenDay(param.getStartDate(), param.getEndDate(), true) + 1;
            for (int t = 0; t < days; t++) {
                //新增
                reservationGroup.setId(snowflake.nextIdStr());
                reservationGroup.setReservationDate(DateUtil.offsetDay(param.getStartDate(), t));
                reservationGroup.setStatus(1);
                reservationGroup.setCreatorId(admin.getUserNo());
                reservationGroup.setCreator(admin.getUserName());
                reservationGroup.setIdOrg(order.getKhdwmcid());
                reservationGroupMapper.insert(reservationGroup);

                //添加预约记录
                reservationService.addGroupReservation(reservationGroup);
            }
        } else {
            ReservationGroup group = reservationGroupMapper.getInfoById(param.getId());
            if (Objects.isNull(group)) {
                throw new ServiceException("该团检预约记录不存在或者已被删除！");
            }
            if (group.getFinishedAm() > 0 || group.getFinishedPm() > 0) {
                throw new ServiceException("已有客户来捡了，不能再更新！");
            }
            //更新
            long count = reservationService.count(new LambdaQueryWrapper<Reservation>().isNotNull(Reservation::getRealName)
                    .eq(Reservation::getReserveGroupId, param.getId()).gt(Reservation::getStatus, 1).eq(Reservation::getIsDelete, 0));
            if (count > 0) {
                throw new ServiceException("已有客户预约了，不能再更新！");
            }
            group.setCountAm(param.getCountAm());
            group.setCountPm(param.getCountPm());
            group.setRemark(param.getRemark());
            group.setModifier(SecurityUtils.getUserNo());
            group.setModifydate(new Date());
            reservationGroupMapper.updateById(group);

            //恢复预约人数
            List<ReservationGroupTime> list = reservationGroupTimeService.list(new LambdaQueryWrapper<ReservationGroupTime>()
                    .eq(ReservationGroupTime::getGroupId, group.getId()));
            for (ReservationGroupTime reservationGroupTime : list) {
                reservationSettingService.updateAbleNumWithRetry(reservationGroupTime.getTimeId(), reservationGroupTime.getCount());
            }
            reservationGroupTimeService.removeBatchByIds(list);

            //重新生成预约记录
            reservationService.addGroupReservation(group);
        }
        return Boolean.TRUE;
    }

    /**
     * 删除团检预约
     *
     * @param ids 对象id集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rmByIds(List<String> ids) {
        for (String id : ids) {
            ReservationGroup group = reservationGroupMapper.getInfoById(id);
            //删除团检预约记录
            reservationGroupMapper.deleteById(id);
            if (Objects.isNull(group)) {
                throw new ServiceException("ID为【" + id + "】的团检预约记录不存在或者已被删除！");
            }
            if (group.getFinishedAm() > 0 || group.getFinishedPm() > 0) {
                throw new ServiceException("ID为【" + id + "】已有客户来捡了，不能再更新！");
            }
            //恢复预约人数
            List<ReservationGroupTime> list = reservationGroupTimeService.list(new LambdaQueryWrapper<ReservationGroupTime>()
                    .eq(ReservationGroupTime::getGroupId, group.getId()));
            for (ReservationGroupTime reservationGroupTime : list) {
                reservationSettingService.updateAbleNumWithRetry(reservationGroupTime.getTimeId(), reservationGroupTime.getCount());
            }
            reservationGroupTimeService.removeBatchByIds(list);
        }
        return Boolean.TRUE;
    }

    /**
     * 获取团队预约信息导出数据
     *
     * @param param
     */
    @Override
    public List<ReservationGroupData> getExportData(RgListParam param) {
        return reservationGroupMapper.getExportData(param);
    }


    /**
     * 获取可预约vip人数
     * @param param
     * @return
     */
    @Override
    public List<ReVipNumberVo> getReVipNumber(GetReVipNumberParam param) {
        List<ReVipNumberVo> list = new ArrayList<>();
        String num = iSysConfigService.selectConfigByKey(Constants.GROUP_RESERVATIONS_VIP_NUMBER);
        long days = DateUtil.betweenDay(param.getStartDate(), param.getEndDate(), true) + 1;
        for (int t = 0; t < days; t++) {
            //日期偏移
            DateTime dateTime = DateUtil.offsetDay(param.getStartDate(), t);
            //可预约 = 可预约总数 - 之前提交的
            Integer num1 = reservationGroupMapper.getReservationNum(param.getIdOrg(),dateTime,null);
            ReVipNumberVo vo = new ReVipNumberVo();
            vo.setReservationDate(dateTime);
            vo.setTotalNum(num);
            vo.setReservationNum(num1.toString());
            list.add(vo);
        }
        return list;
    }
}

