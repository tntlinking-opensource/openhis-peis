package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.TpWisdomUpload;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 长沙-株洲智慧医疗对接(TpWisdomUpload)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:38
 */
public interface TpWisdomUploadService extends IService<TpWisdomUpload> {

    /**
     * 分页查询[长沙-株洲智慧医疗对接]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TpWisdomUpload> getPage(PageParam<TpWisdomUpload> page, TpWisdomUpload param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TpWisdomUpload getInfoById(String id);

}

