package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.DevicetypePositionCheckitem;
import com.center.medical.data.bean.vo.PostionCheckItemVo;

/**
 * 东软pacs部位方法基础表(DevicetypePositionCheckitem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:22
 */
public interface DevicetypePositionCheckitemService extends IService<DevicetypePositionCheckitem> {

    /**
     * 分页查询[东软pacs部位方法基础表]列表
     *
     * @param page  分页参数
     * @param param DevicetypePositionCheckitem查询参数
     * @return 分页数据
     */
    IPage<DevicetypePositionCheckitem> getList(PageParam<DevicetypePositionCheckitem> page, DevicetypePositionCheckitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DevicetypePositionCheckitem getInfoById(String id);

    /**
     * 获取部位检查项目数据
     *
     * @param key
     * @param id
     * @return
     */
    IPage<PostionCheckItemVo> getPostionCheckItemData(PageParam<Basexamltem> page, String key, String id);
}

