package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.param.PoGroupParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import com.center.medical.sellcrm.bean.param.DbOrderParam;
import com.center.medical.sellcrm.bean.vo.POSimpleVo;

/**
 * 体检者团体任务(Peisorgreservation)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:01
 */
public interface PeisorgreservationService extends IService<Peisorgreservation> {

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peisorgreservation> getPage(PageParam<Peisorgreservation> page, DbOrderParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservation getInfoById(String id);

    /**
     * 登记页面团体列表
     *
     * @param param
     * @return
     */
    IPage<POSimpleVo> getGroupForOrgData(PageParam<Peisorgreservation> page, PoGroupParam param);
}

