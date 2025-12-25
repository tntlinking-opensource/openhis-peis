package com.center.medical.enterprise.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.enterprise.bean.model.Peispatient;
import com.center.medical.enterprise.bean.vo.KongzhitaiDataVo;
import com.center.medical.enterprise.common.util.PageParam;


import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:51
 */
public interface PeispatientService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getPage(PageParam<Peispatient> page, Peispatient param);


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);




    /**
     * 通过体检码获取记录
     *
     * @param patientCode
     * @return
     */
    Peispatient getByPatientCode(String patientCode);


    /**
     * 获取控制台界面数据
     * @return
     */
    KongzhitaiDataVo getKongzhitaiData();

    /**
     * 获取同一个档案号的其他体检号
     * @param patientarchiveno
     * @param patientcode
     * @return
     */
    List<String> getOtherCode(String patientarchiveno, String patientcode);
}

