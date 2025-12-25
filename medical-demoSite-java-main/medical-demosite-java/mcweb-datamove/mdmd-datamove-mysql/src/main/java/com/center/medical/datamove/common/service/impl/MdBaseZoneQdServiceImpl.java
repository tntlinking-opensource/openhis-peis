package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBaseZoneQdMapper;
import com.center.medical.datamove.common.bean.model.MdBaseZoneQd;
import com.center.medical.datamove.common.service.MdBaseZoneQdService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 所属地区(MdBaseZoneQd)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:11
 */
@Slf4j
@Service("mdBaseZoneQdService")
@RequiredArgsConstructor
public class MdBaseZoneQdServiceImpl extends ServiceImpl<MdBaseZoneQdMapper, MdBaseZoneQd> implements MdBaseZoneQdService {

    private final MdBaseZoneQdMapper mdBaseZoneQdMapper;

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param MdBaseZoneQd查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBaseZoneQd> getPage(PageParam<MdBaseZoneQd> page, MdBaseZoneQd param) {
        return mdBaseZoneQdMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBaseZoneQd getInfoById(String id) {
        return mdBaseZoneQdMapper.getInfoById(id);
    }

}


