package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeisState;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者上传状态(PeisState)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
public interface PeisStateService extends IService<PeisState> {

    /**
     * 分页查询[体检者上传状态]列表
     *
     * @param page  分页参数
     * @param param PeisState查询参数
     * @return 分页数据
     */
    IPage<PeisState> getList(PageParam<PeisState> page, PeisState param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeisState getInfoById(String id);

    /**
     * 设置上传标示
     *
     * @param patientcode
     * @param scbs
     * @return
     */
    PeisState setScbs(String patientcode, int scbs);

    /**
     * 记录体检者状态
     *
     * @param peispatient
     * @param value
     */
    void saOrUp(Peispatient peispatient, int value);


    /**
     * 根据体检号获取记录详情
     *
     * @param patientcode 体检号
     */
    PeisState getByPatientcode(String patientcode);
}

