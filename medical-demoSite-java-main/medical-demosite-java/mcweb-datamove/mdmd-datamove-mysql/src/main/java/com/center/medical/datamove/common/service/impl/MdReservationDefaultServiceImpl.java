package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReservationDefaultMapper;
import com.center.medical.datamove.common.bean.model.MdReservationDefault;
import com.center.medical.datamove.common.service.MdReservationDefaultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 预约各检区默认设置(MdReservationDefault)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:18
 */
@Slf4j
@Service("mdReservationDefaultService")
@RequiredArgsConstructor
public class MdReservationDefaultServiceImpl extends ServiceImpl<MdReservationDefaultMapper, MdReservationDefault> implements MdReservationDefaultService {

    private final MdReservationDefaultMapper mdReservationDefaultMapper;

    /**
     * 分页查询[预约各检区默认设置]列表
     *
     * @param page  分页参数
     * @param param MdReservationDefault查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReservationDefault> getPage(PageParam<MdReservationDefault> page, MdReservationDefault param) {
        return mdReservationDefaultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReservationDefault getInfoById(Object id) {
        return mdReservationDefaultMapper.getInfoById(id);
    }

}


