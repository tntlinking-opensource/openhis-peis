package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Peisorgreservation;

import java.util.List;

/**
 * 体检团体任务表(Peisorgreservation)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:54:57
 */
public interface OrPeisorgreservationService extends IService<Peisorgreservation> {

    /**
     * 分页查询[体检团体任务表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peisorgreservation> getPage(PageParam<Peisorgreservation> page, Peisorgreservation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservation getInfoById(String id);

    /**
     * 查询未结束的任务
     *
     * @return
     */
    List<Peisorgreservation> getUnFinished();

    /**
     * 通过订单号查询体检者团体任务
     * @param ddh
     * @return
     */
    Peisorgreservation getByDdh(String ddh);
}

