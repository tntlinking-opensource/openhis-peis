package com.center.medical.query.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.FinishStatusParam;
import com.center.medical.query.bean.vo.FCChargeDataVo;
import com.center.medical.query.bean.vo.FinishStatusVo;

/**
 * 未检项目查询(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-13 16:32:13
 */
public interface FinishStatusService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FinishStatusVo> getList(PageParam<FinishStatusVo> page, FinishStatusParam param);


    /**
     * 点击获取收费项目信息
     * @param page
     * @param patientcode
     * @return
     */
    IPage<FCChargeDataVo> getChargeData(PageParam<FCChargeDataVo> page, String patientcode);
}

