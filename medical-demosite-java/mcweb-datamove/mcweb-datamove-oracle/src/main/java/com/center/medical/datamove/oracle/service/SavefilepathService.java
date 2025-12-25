package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Savefilepath;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 存放文件路径表(Savefilepath)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:38
 */
public interface SavefilepathService extends IService<Savefilepath> {

    /**
     * 分页查询[存放文件路径表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Savefilepath> getPage(PageParam<Savefilepath> page, Savefilepath param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Savefilepath getInfoById(String id);

}

