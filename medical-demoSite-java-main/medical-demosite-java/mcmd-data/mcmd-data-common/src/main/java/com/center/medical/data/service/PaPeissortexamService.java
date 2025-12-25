package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.PaPeissortexam;
import com.center.medical.data.bean.param.PaPeiEParam;

/**
 * 平安软件-排检(PaPeissortexam)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 11:26:55
 */
public interface PaPeissortexamService extends IService<PaPeissortexam> {

    /**
     * 分页查询[平安软件-排检]列表
     *
     * @param page  分页参数
     * @param param PaPeissortexam查询参数
     * @return 分页数据
     */
    IPage<PaPeissortexam> getList(PageParam<PaPeissortexam> page, PaPeiEParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id PaPeissortexam查询参数
     * @return 分页数据
     */
    PaPeissortexam getInfoById(String id);

    /**
     * 新增或者更新
     *
     * @param paPeissortexam
     * @return
     */
    Boolean saOrUp(PaPeissortexam paPeissortexam);
}

