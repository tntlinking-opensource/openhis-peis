package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.DevicetypePositionCheckitem;
import com.center.medical.data.bean.vo.PostionCheckItemVo;
import org.apache.ibatis.annotations.Param;

/**
 * 东软pacs部位方法基础表(DevicetypePositionCheckitem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:22
 */
public interface DevicetypePositionCheckitemMapper extends BaseMapper<DevicetypePositionCheckitem> {

    /**
     * 分页查询[东软pacs部位方法基础表]列表
     *
     * @param page  分页参数
     * @param param DevicetypePositionCheckitem查询参数
     * @return 分页数据
     */
    IPage<DevicetypePositionCheckitem> getList(PageParam<DevicetypePositionCheckitem> page, @Param("param") DevicetypePositionCheckitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DevicetypePositionCheckitem getInfoById(@Param("id") String id);

    /**
     * 获取部位检查项目数据
     *
     * @param key
     * @param id
     * @return
     */
    IPage<PostionCheckItemVo> getPostionCheckItemData(PageParam<Basexamltem> page, @Param("key") String key, @Param("id") String id);
}
