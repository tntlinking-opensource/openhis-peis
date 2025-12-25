package com.center.medical.reception.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.reception.bean.param.BillModelDataParam;

import java.util.List;
import java.util.Map;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-20 15:01:15
 */
public interface GuideSheetService extends IService<Peispatient> {


    /**
     * 导引单数据打印
     * @param param
     * @return
     */
    List<Map<String, Object>> getBillModelData(BillModelDataParam param);


    /**
     * 根据id获取多位体检者
     * @param param
     * @return
     */
    Map<String, Object> getPatientsForID(BillModelDataParam param);
}

