package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.DwHarm;


/**
 * 单位危害因素(DwHarm)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
public interface DwHarmService extends IService<DwHarm> {

    /**
     * 分页查询[单位危害因素]列表
     *
     * @param page  分页参数
     * @param param DwHarm查询参数
     * @return 分页数据
     */
    IPage<DwHarm> getList(PageParam<DwHarm> page, DwHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DwHarm getInfoById(String id);

    /**
     * 单位职业病危害因素数据假删
     * @param ids
     * @return
     */
    String removeDwHarm(String ids);


    /**
     * 新增或修改
     * @param dwHarm
     * @return
     */
    String saveOrUpdateDwHarm(DwHarm dwHarm);
}

