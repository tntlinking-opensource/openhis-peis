package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmSellprintteams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 销售打印项目分类设置表(CrmSellprintteams)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:01
 */
public interface CrmSellprintteamsService extends IService<CrmSellprintteams> {

    /**
     * 分页查询[销售打印项目分类设置表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmSellprintteams> getPage(PageParam<CrmSellprintteams> page, CrmSellprintteams param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSellprintteams getInfoById(String id);

}

