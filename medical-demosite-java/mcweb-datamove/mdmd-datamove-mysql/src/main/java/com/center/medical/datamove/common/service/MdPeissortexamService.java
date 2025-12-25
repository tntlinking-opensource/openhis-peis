package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeissortexam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 排检(MdPeissortexam)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
public interface MdPeissortexamService extends IService<MdPeissortexam> {

    /**
     * 分页查询[排检]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeissortexam> getPage(PageParam<MdPeissortexam> page, MdPeissortexam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeissortexam getInfoById(String id);

}

