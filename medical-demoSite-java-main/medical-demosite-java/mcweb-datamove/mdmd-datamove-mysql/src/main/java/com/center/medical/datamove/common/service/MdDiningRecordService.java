package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdDiningRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 就餐记录(MdDiningRecord)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
public interface MdDiningRecordService extends IService<MdDiningRecord> {

    /**
     * 分页查询[就餐记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdDiningRecord> getPage(PageParam<MdDiningRecord> page, MdDiningRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDiningRecord getInfoById(String id);

}

