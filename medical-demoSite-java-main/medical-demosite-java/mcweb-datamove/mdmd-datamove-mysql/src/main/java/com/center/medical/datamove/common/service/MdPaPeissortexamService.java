package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPaPeissortexam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 平安软件-排检(MdPaPeissortexam)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
public interface MdPaPeissortexamService extends IService<MdPaPeissortexam> {

    /**
     * 分页查询[平安软件-排检]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPaPeissortexam> getPage(PageParam<MdPaPeissortexam> page, MdPaPeissortexam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPaPeissortexam getInfoById(String id);

}

