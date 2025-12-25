package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientexamitem;

/**
 * LIS结果(LisPacs数据)(MdPeispatientexamitem)服务接口
 *
 * @author ay
 * @since 2024-06-05 15:19:05
 */
public interface MdPeispatientexamitemService extends IService<MdPeispatientexamitem> {

    /**
     * 分页查询[LIS结果(LisPacs数据)]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientexamitem> getPage(PageParam<MdPeispatientexamitem> page, MdPeispatientexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientexamitem getInfoById(String id);

}

