package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.common.bean.model.SysCodeConfig;

/**
 * 加密密钥及授权码表(CodeConfig)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
public interface SysCodeConfigService extends IService<SysCodeConfig> {

    /**
     * 分页查询[加密密钥及授权码表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysCodeConfig> getPage(PageParam<SysCodeConfig> page, SysCodeConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysCodeConfig getInfoById(Object id);

}

