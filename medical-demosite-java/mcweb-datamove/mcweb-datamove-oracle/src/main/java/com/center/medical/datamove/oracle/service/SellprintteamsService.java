package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Sellprintteams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 销售打印项目分类设置表(Sellprintteams)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:55
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

