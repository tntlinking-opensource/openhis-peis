package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReservationMapper;
import com.center.medical.datamove.common.bean.model.MdReservation;
import com.center.medical.datamove.common.service.MdReservationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检预约记录(MdReservation)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:18
 */
@Slf4j
@Service("mdReservationService")
@RequiredArgsConstructor
public class MdReservationServiceImpl extends ServiceImpl<MdReservationMapper, MdReservation> implements MdReservationService {

    private final MdReservationMapper mdReservationMapper;

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param MdReservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReservation> getPage(PageParam<MdReservation> page, MdReservation param) {
        return mdReservationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReservation getInfoById(Long id) {
        return mdReservationMapper.getInfoById(id);
    }

}


