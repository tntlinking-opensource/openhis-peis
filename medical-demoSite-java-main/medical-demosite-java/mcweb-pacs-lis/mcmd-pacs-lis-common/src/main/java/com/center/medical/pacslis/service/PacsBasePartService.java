package com.center.medical.pacslis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacslis.bean.model.PacsBasePart;

/**
 * PACS-部位(PacsBasePart)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
public interface PacsBasePartService extends IService<PacsBasePart> {

    /**
     * 分页查询[PACS-部位]列表
     *
     * @param page  分页参数
     * @return 分页数据
     */
    IPage<PacsBasePart> getPage(PageParam<PacsBasePart> page);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsBasePart getInfoById(String id);

    /**
     * 添加或修改
     * @param pacsBasePart
     * @return
     */
    Boolean saOrUp(PacsBasePart pacsBasePart);
}

