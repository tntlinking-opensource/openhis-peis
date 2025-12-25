package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysLogininfor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 系统访问记录(SysLogininfor)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
public interface SysLogininforService extends IService<SysLogininfor> {

    /**
     * 分页查询[系统访问记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysLogininfor> getPage(PageParam<SysLogininfor> page, SysLogininfor param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键infoId
     * @return 详情信息
     */
    SysLogininfor getInfoById(Long id);

}

