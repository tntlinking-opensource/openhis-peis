package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeisorgreservation;

import java.util.List;

/**
 * 体检团体任务表(Peisorgreservation)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:54:57
 */
public interface OrPeisorgreservationService extends IService<OrPeisorgreservation> {

    /**
     * 分页查询[体检团体任务表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrPeisorgreservation> getPage(PageParam<OrPeisorgreservation> page, OrPeisorgreservation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeisorgreservation getInfoById(String id);

    /**
     * 查询未结束的任务
     *
     * @return
     */
    List<OrPeisorgreservation> getUnFinished();

    /**
     * 通过订单号查询
     * @param ddh
     * @return
     */
    OrPeisorgreservation getByDdh(String ddh);
}

