package com.center.medical.app.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.dto.SysBranchDto;
import com.center.medical.app.bean.model.SysBranch;
import com.center.medical.app.bean.param.DistanceParam;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.ServerResponse;
import com.center.medical.app.common.response.ServerResponseEntity;
import com.center.medical.app.common.util.Arith;
import com.center.medical.app.service.SysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分中心接口
 *
 * @author 路飞船长
 * @since 2023-03-28 18:26:14
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "分中心接口")
@RequestMapping("sysBranch")
public class SysBranchController {
    /**
     * 服务对象
     */
    private final SysBranchService sysBranchService;
    private final MapperFacade mapperFacade;
    private static final int EARTH_RADIUS = 6371; // 地球半径，单位千米

    /**
     * 获取分中心列表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取分中心列表", notes = "获取分中心列表")
    public ServerResponse<List<SysBranchDto>> list(DistanceParam param) {
        List<SysBranch> list = this.sysBranchService.list(new LambdaQueryWrapper<SysBranch>()
                .eq(SysBranch::getIsDelete, 0).eq(SysBranch::getIsShow, 1).eq(SysBranch::getNeedReservation, 1));
        if (CollectionUtil.isEmpty(list)) {
            throw new AppBindException("目前没有可供预约的分中心，请直接联系客服！");
        }

        List<SysBranchDto> sysBranchVos = mapperFacade.mapAsList(list, SysBranchDto.class);
        if (param.getLat() != 0.0 && param.getLng() != 0.0){
            //传了经纬度就计算距离
            for (SysBranchDto vo : sysBranchVos) {
                if (StringUtils.isNotEmpty(vo.getLat()) && StringUtils.isNotEmpty(vo.getLng())){
                    double distance = calculateDistance(param.getLat(), param.getLng(), Double.valueOf(vo.getLat()), Double.valueOf(vo.getLng()));
                    vo.setDistance(distance);
                }
            }
            //重新排序,按照distance排序，null值排在最后面
            sysBranchVos = sysBranchVos.stream()
                    .sorted(Comparator.comparing(SysBranchDto::getDistance, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());
        }

        return ServerResponseEntity.success(sysBranchVos);
    }



    /**
     * 计算两个经纬度点之间的距离
     * @param lat1 第一个点的纬度
     * @param lon1 第一个点的经度
     * @param lat2 第二个点的纬度
     * @param lon2 第二个点的经度
     * @return 距离（单位：千米）
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return  Arith.round(Arith.mul(EARTH_RADIUS, c),2);
    }

}

