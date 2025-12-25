package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.WsBkDictionary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 网站字典(WsBkDictionary)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
public interface WsBkDictionaryService extends IService<WsBkDictionary> {

    /**
     * 分页查询[网站字典]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WsBkDictionary> getPage(PageParam<WsBkDictionary> page, WsBkDictionary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WsBkDictionary getInfoById(String id);

}

