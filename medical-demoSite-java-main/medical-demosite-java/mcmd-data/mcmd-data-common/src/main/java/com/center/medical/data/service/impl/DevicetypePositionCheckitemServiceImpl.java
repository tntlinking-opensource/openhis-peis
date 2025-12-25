package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.DevicetypePositionCheckitem;
import com.center.medical.data.bean.vo.PostionCheckItemVo;
import com.center.medical.data.dao.DevicetypePositionCheckitemMapper;
import com.center.medical.data.service.DevicetypePositionCheckitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 东软pacs部位方法基础表(DevicetypePositionCheckitem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:22
 */
@Slf4j
@Service("devicetypePositionCheckitemService")
@RequiredArgsConstructor
public class DevicetypePositionCheckitemServiceImpl extends ServiceImpl<DevicetypePositionCheckitemMapper, DevicetypePositionCheckitem> implements DevicetypePositionCheckitemService {

    private final DevicetypePositionCheckitemMapper devicetypePositionCheckitemMapper;

    /**
     * 分页查询[东软pacs部位方法基础表]列表
     *
     * @param page  分页参数
     * @param param DevicetypePositionCheckitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DevicetypePositionCheckitem> getList(PageParam<DevicetypePositionCheckitem> page, DevicetypePositionCheckitem param) {
        return devicetypePositionCheckitemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DevicetypePositionCheckitem getInfoById(String id) {
        return devicetypePositionCheckitemMapper.getInfoById(id);
    }


    /**
     * 获取部位检查项目数据
     *
     * @param key
     * @param id
     * @return
     */
    @Override
    public IPage<PostionCheckItemVo> getPostionCheckItemData(PageParam<Basexamltem> page, String key, String id) {
        return devicetypePositionCheckitemMapper.getPostionCheckItemData(page, key, id);
    }
}

