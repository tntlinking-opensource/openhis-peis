package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Area;
import com.center.medical.common.utils.page.PageParam;

/**
 * 籍贯表(Area)表服务接口
 *
 * @author ay
 * @since 2022-11-18 14:45:14
 */
public interface AreaService extends IService<Area> {

/**
* 分页查询[籍贯表]列表
*
* @param page 分页参数
* @param param 查询参数
* @return 分页数据
*/
IPage<Area> getList(PageParam<Area> page, Area param);

/**
* 根据主键id获取记录详情
*
* @param id 主键id
* @return 详情信息
*/
    Area getInfoById(String id);

}

