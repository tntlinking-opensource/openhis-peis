package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.OccupationDiseast;

/**
 * JC职业病名称(OccupationDiseast)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:19
 */
public interface OccupationDiseastService extends IService<OccupationDiseast> {

    /**
     * 分页查询[JC职业病名称]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseast查询参数
     * @return 分页数据
     */
    IPage<OccupationDiseast> getList(PageParam<OccupationDiseast> page, OccupationDiseast param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OccupationDiseast getInfoById(String id);

    /**
     * 保存或更新
     * @param occupationDiseast
     * @return
     */
    String saveOrUpdateOccu(OccupationDiseast occupationDiseast);

    /**
     * 逻辑删除
     * @param ids
     * @return
     */
    String removeOccupa(String ids);

    /**
     * 根据输入码获取职业病
     * @param page
     * @param inputCode
     * @return
     */
    IPage<OccupationDiseast> getZybData(PageParam<OccupationDiseast> page, String inputCode);
}

