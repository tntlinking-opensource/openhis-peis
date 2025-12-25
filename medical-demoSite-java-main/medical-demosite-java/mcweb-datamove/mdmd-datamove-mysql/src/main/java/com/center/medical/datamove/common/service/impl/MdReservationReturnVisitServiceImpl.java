package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReservationReturnVisitMapper;
import com.center.medical.datamove.common.bean.model.MdReservationReturnVisit;
import com.center.medical.datamove.common.service.MdReservationReturnVisitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 预约回访记录(MdReservationReturnVisit)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:19
 */
@Slf4j
@Service("mdReservationReturnVisitService")
@RequiredArgsConstructor
public class MdReservationReturnVisitServiceImpl extends ServiceImpl<MdReservationReturnVisitMapper, MdReservationReturnVisit> implements MdReservationReturnVisitService {

    private final MdReservationReturnVisitMapper mdReservationReturnVisitMapper;

    /**
     * 分页查询[预约回访记录]列表
     *
     * @param page  分页参数
     * @param param MdReservationReturnVisit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReservationReturnVisit> getPage(PageParam<MdReservationReturnVisit> page, MdReservationReturnVisit param) {
        return mdReservationReturnVisitMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReservationReturnVisit getInfoById(String id) {
        return mdReservationReturnVisitMapper.getInfoById(id);
    }

}


