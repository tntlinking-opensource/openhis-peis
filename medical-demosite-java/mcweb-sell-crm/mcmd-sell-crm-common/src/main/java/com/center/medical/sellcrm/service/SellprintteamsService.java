package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Sellprintteams;

/**
 * 销售打印项目分类设置表(Sellprintteams)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:25
 */
public interface SellprintteamsService extends IService<Sellprintteams> {

    /**
     * 分页查询[销售打印项目分类设置表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Sellprintteams> getPage(PageParam<Sellprintteams> page, Sellprintteams param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sellprintteams getInfoById(String id);

}

