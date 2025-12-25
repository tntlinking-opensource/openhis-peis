package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.PatientListParam;
import com.center.medical.finance.bean.param.PingAnPageParam;
import com.center.medical.finance.bean.vo.PatientListVo;
import com.center.medical.finance.bean.vo.PingAnPageVo;
import com.center.medical.sellcrm.bean.model.Createorder;

import java.util.List;

/**
 * 订单表(Createorder)表服务接口
 *
 * @author ay
 * @since 2023-04-03 14:19:37
 */
public interface PingAnStatementService extends IService<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PingAnPageVo> getList(PageParam<PingAnPageVo> page, PingAnPageParam param);
    

    /**
     * 获取体检者数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<PatientListVo> getPatientListData(PageParam<PatientListVo> page, PatientListParam param);

    /**
     * 导出体检人员
     * @param param
     * @return
     */
    List<PatientListVo> exportOrderPatient(PatientListParam param);
}

