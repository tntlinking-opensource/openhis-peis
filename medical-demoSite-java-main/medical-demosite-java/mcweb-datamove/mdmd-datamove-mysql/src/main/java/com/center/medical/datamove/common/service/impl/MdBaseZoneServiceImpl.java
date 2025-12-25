package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBaseZoneMapper;
import com.center.medical.datamove.common.bean.model.MdBaseZone;
import com.center.medical.datamove.common.service.MdBaseZoneService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 所属地区(MdBaseZone)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:11
 */
@Slf4j
@Service("mdBaseZoneService")
@RequiredArgsConstructor
public class MdBaseZoneServiceImpl extends ServiceImpl<MdBaseZoneMapper, MdBaseZone> implements MdBaseZoneService {

    private final MdBaseZoneMapper mdBaseZoneMapper;

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param MdBaseZone查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBaseZone> getPage(PageParam<MdBaseZone> page, MdBaseZone param) {
        return mdBaseZoneMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBaseZone getInfoById(String id) {
        return mdBaseZoneMapper.getInfoById(id);
    }

}


