package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.OccupationDrug;

/**
 * JC职业病/禁忌症(OccupationDrug)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:18
 */
public interface OccupationDrugService extends IService<OccupationDrug> {

    /**
     * 分页查询[JC职业病/禁忌症]列表
     *
     * @param page  分页参数
     * @param param OccupationDrug查询参数
     * @return 分页数据
     */
    IPage<OccupationDrug> getList(PageParam<OccupationDrug> page, OccupationDrug param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OccupationDrug getInfoById(String id);

    /**
     * 保存或更新
     * @param occupationDrug
     * @return
     */
    Boolean saveOrUpdateOccup(OccupationDrug occupationDrug);

    /**
     * 删除
     * @param ids
     * @return
     */
    String removeOccupa(String ids);
}

