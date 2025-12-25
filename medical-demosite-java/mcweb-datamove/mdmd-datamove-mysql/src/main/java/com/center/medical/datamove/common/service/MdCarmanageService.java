package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdCarmanage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检车管理(MdCarmanage)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
public interface MdCarmanageService extends IService<MdCarmanage> {

    /**
     * 分页查询[体检车管理]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCarmanage> getPage(PageParam<MdCarmanage> page, MdCarmanage param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCarmanage getInfoById(String id);

}

