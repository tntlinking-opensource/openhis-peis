package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReservationSettingMapper;
import com.center.medical.datamove.common.bean.model.MdReservationSetting;
import com.center.medical.datamove.common.service.MdReservationSettingService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 预约各检区设置(MdReservationSetting)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:19
 */
@Slf4j
@Service("mdReservationSettingService")
@RequiredArgsConstructor
public class MdReservationSettingServiceImpl extends ServiceImpl<MdReservationSettingMapper, MdReservationSetting> implements MdReservationSettingService {

    private final MdReservationSettingMapper mdReservationSettingMapper;

    /**
     * 分页查询[预约各检区设置]列表
     *
     * @param page  分页参数
     * @param param MdReservationSetting查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReservationSetting> getPage(PageParam<MdReservationSetting> page, MdReservationSetting param) {
        return mdReservationSettingMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReservationSetting getInfoById(Object id) {
        return mdReservationSettingMapper.getInfoById(id);
    }

}


