package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDevicetypePositionCheckitemMapper;
import com.center.medical.datamove.common.bean.model.MdDevicetypePositionCheckitem;
import com.center.medical.datamove.common.service.MdDevicetypePositionCheckitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 东软pacs部位方法基础表(MdDevicetypePositionCheckitem)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:21
 */
@Slf4j
@Service("mdDevicetypePositionCheckitemService")
@RequiredArgsConstructor
public class MdDevicetypePositionCheckitemServiceImpl extends ServiceImpl<MdDevicetypePositionCheckitemMapper, MdDevicetypePositionCheckitem> implements MdDevicetypePositionCheckitemService {

    private final MdDevicetypePositionCheckitemMapper mdDevicetypePositionCheckitemMapper;

    /**
     * 分页查询[东软pacs部位方法基础表]列表
     *
     * @param page  分页参数
     * @param param MdDevicetypePositionCheckitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDevicetypePositionCheckitem> getPage(PageParam<MdDevicetypePositionCheckitem> page, MdDevicetypePositionCheckitem param) {
        return mdDevicetypePositionCheckitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDevicetypePositionCheckitem getInfoById(String id) {
        return mdDevicetypePositionCheckitemMapper.getInfoById(id);
    }

}


