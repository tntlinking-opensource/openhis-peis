package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdComboexamitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 用于判断职业小结(MdComboexamitem)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
public interface MdComboexamitemService extends IService<MdComboexamitem> {

    /**
     * 分页查询[用于判断职业小结]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdComboexamitem> getPage(PageParam<MdComboexamitem> page, MdComboexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdComboexamitem getInfoById(String id);

}

