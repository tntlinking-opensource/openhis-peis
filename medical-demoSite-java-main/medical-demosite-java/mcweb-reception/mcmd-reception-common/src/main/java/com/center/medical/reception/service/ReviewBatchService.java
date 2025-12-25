package com.center.medical.reception.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.reception.bean.param.RBatchParam;
import com.center.medical.reception.bean.param.ReviewBatchParam;
import com.center.medical.reception.bean.vo.RBListDataVo;
import com.center.medical.reception.bean.vo.ReviewBatchVo;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-02-02 15:30:33
 */
public interface ReviewBatchService extends IService<Peispatient> {

    /**
    * 分页查询[QT体检者表]列表
    *
    * @param param 查询参数
    * @return 分页数据
    */
    List<ReviewBatchVo> getItemsListData(ReviewBatchParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Peispatient getInfoById(String id);

    /**
     * 职业复查获取数据
     * @param param
     * @return
     */
    List<RBListDataVo> getListData(ReviewBatchParam param);

    /**
     * 职业复查保存
     * @param rBatchParam
     * @return
     */
    Boolean reviewBatch(RBatchParam rBatchParam);
}

