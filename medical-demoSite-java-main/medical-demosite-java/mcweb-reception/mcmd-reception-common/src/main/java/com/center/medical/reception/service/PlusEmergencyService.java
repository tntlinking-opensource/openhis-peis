package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.param.PlusEmParam;
import com.center.medical.reception.bean.vo.PlusEmergencyVo;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-02-02 15:30:33
 */
public interface PlusEmergencyService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page 分页参数
     * @return 分页数据
     */
    IPage<PlusEmergencyVo> getList(PageParam<PlusEmergencyVo> page, PlusEmParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 加急-保存
     *
     * @param patientcode
     * @return
     */
    Boolean SaOrUp(String patientcode);
}

