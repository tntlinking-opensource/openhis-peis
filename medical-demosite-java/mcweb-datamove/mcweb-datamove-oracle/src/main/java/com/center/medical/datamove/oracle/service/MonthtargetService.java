package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Monthtarget;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 销售月度计划(Monthtarget)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:07
 */
public interface MonthtargetService extends IService<Monthtarget> {

    /**
     * 分页查询[销售月度计划]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Monthtarget> getPage(PageParam<Monthtarget> page, Monthtarget param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Monthtarget getInfoById(String id);

}

