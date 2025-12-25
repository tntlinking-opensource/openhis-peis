package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.dto.SysVersionItemDto;
import com.center.medical.system.bean.model.SysVersionItem;
import com.center.medical.system.bean.param.SysVersionItemParam;

import java.util.List;

/**
 * 系统更新记录(SysVersionItem)服务接口
 *
 * @author makejava
 * @since 2024-03-01 18:02:37
 */
public interface SysVersionItemService extends IService<SysVersionItem> {

    /**
     * 分页查询[系统更新记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysVersionItem> getPage(PageParam<SysVersionItem> page, SysVersionItemParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键logId
     * @return 详情信息
     */
    SysVersionItem getInfoById(Integer id);

    boolean importItem(List<SysVersionItemDto> list, String operName);
}

